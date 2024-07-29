package com.pinslog.samples.data

import kotlinx.coroutines.flow.Flow

interface FakeDataRepo {

    fun getFakeData(isContinue: Boolean): Flow<Int?>
    // stop 함수도 필요한가?
}