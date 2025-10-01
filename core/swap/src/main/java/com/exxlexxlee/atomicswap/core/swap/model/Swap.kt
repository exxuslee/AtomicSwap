package com.exxlexxlee.atomicswap.core.swap.model

class Swap {

    var id = ""
    var initiator = false

    var state = State.REQUESTED

    var initiatorCoinCode = ""
    var responderCoinCode = ""

    var initiatorRedeemPKH = byteArrayOf()
    var initiatorRedeemPKId = ""
    var initiatorRefundPKH = byteArrayOf()
    var initiatorRefundPKId = ""
    var initiatorRefundTime = 0L
    var initiatorAmount = "0.0"
    var secret = byteArrayOf()
    var secretHash = byteArrayOf()
    var responderRedeemPKH = byteArrayOf()
    var responderRedeemPKId = ""
    var responderRefundPKH = byteArrayOf()
    var responderRefundPKId = ""
    var responderRefundTime = 0L
    var responderAmount = "0.0"

    var initiatorBailTx: ByteArray = byteArrayOf()
    var responderBailTx: ByteArray = byteArrayOf()
    var initiatorRedeemTx: ByteArray = byteArrayOf()
    var initiatorRevealTx: ByteArray = byteArrayOf()
    var responderRedeemTx: ByteArray = byteArrayOf()
    var refundTx: ByteArray = byteArrayOf()
    var cdt = System.currentTimeMillis()
    var isReactive: Boolean = false

    enum class State(val value: Int) {
        REQUESTED(1),
        RESPONDED(2),
        INITIATOR_BAILED(3),
        RESPONDER_BAILED(4),
        INITIATOR_REDEEMED(5),
        RESPONDER_REDEEMED(6),
        REFUNDED(7),
        REVEALED(8);

    }

}