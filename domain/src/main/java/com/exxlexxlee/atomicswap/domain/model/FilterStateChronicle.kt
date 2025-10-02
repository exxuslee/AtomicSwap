package com.exxlexxlee.atomicswap.domain.model

import com.exxlexxlee.atomicswap.domain.R

enum class FilterStateChronicle(val icon: Int) {
    MAKE(R.drawable.outline_book_2_24),
    ACTIVE(R.drawable.outline_gavel_24),
    COMPLETE(R.drawable.outline_handshake_24),
    REFUND(R.drawable.outline_undo_24);
}