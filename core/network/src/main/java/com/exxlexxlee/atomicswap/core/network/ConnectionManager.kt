package com.exxlexxlee.atomicswap.core.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


class ConnectionManager(context: Context) {
    private val activeNetworks = mutableSetOf<Network>()
    private val connectivityManager = context.getSystemService(ConnectivityManager::class.java)

    private val _connectionState = MutableStateFlow(getCurrentConnectionState())
    val connectionState: StateFlow<ConnectionState> = _connectionState.asStateFlow()

    private var hasValidatedInternet = false

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {

        override fun onAvailable(network: Network) {
            activeNetworks.add(network)
            updateConnectionState()
        }

        override fun onCapabilitiesChanged(
            network: Network,
            capabilities: NetworkCapabilities
        ) {
            hasValidatedInternet = capabilities.isInternetAvailable()
            updateConnectionState()
        }

        override fun onLost(network: Network) {
            activeNetworks.remove(network)
            hasValidatedInternet = checkIfAnyNetworkHasInternet()
            updateConnectionState()
        }

        override fun onUnavailable() {
            activeNetworks.clear()
            hasValidatedInternet = false
            updateConnectionState()
        }
    }

    init {
        val request = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .addCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
            .build()

        connectivityManager.registerNetworkCallback(request, networkCallback)
    }


    private fun getCurrentConnectionState(): ConnectionState {
        val network = connectivityManager.activeNetwork
        val capabilities = network?.let { connectivityManager.getNetworkCapabilities(it) }

        return if (capabilities != null && capabilities.isInternetAvailable()) {
            hasValidatedInternet = true
            activeNetworks.add(network)
            ConnectionState.Connected(getNetworkType(capabilities))
        } else {
            hasValidatedInternet = false
            ConnectionState.Disconnected
        }
    }

    private fun updateConnectionState() {
        val hasConnection = activeNetworks.isNotEmpty()
        val newState = when {
            hasConnection && hasValidatedInternet -> {
                val networkType = getActiveNetworkType()
                ConnectionState.Connected(networkType)
            }
            hasConnection && !hasValidatedInternet -> ConnectionState.ConnectedNoInternet
            else -> ConnectionState.Disconnected
        }

        if (_connectionState.value != newState) {
            _connectionState.value = newState
        }
    }

    private fun checkIfAnyNetworkHasInternet(): Boolean {
        return activeNetworks.any { network ->
            connectivityManager.getNetworkCapabilities(network)?.isInternetAvailable() == true
        }
    }

    private fun getActiveNetworkType(): NetworkType {
        val network = connectivityManager.activeNetwork ?: return NetworkType.UNKNOWN
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return NetworkType.UNKNOWN
        return getNetworkType(capabilities)
    }

    private fun getNetworkType(capabilities: NetworkCapabilities): NetworkType = when {
        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> NetworkType.WIFI
        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> NetworkType.CELLULAR
        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> NetworkType.ETHERNET
        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_VPN) -> NetworkType.VPN
        else -> NetworkType.UNKNOWN
    }

    private fun NetworkCapabilities.isInternetAvailable(): Boolean {
        return hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
                hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
    }

    /**
     * Unregisters network callback and cleans up resources
     */
    fun release() {
        runCatching {
            connectivityManager.unregisterNetworkCallback(networkCallback)
            activeNetworks.clear()
            hasValidatedInternet = false
        }
    }

    sealed class ConnectionState() {
        data class Connected(val networkType: NetworkType) : ConnectionState()
        data object ConnectedNoInternet : ConnectionState()
        data object Disconnected : ConnectionState()
    }

    enum class NetworkType {
        WIFI,
        CELLULAR,
        ETHERNET,
        VPN,
        UNKNOWN
    }
}