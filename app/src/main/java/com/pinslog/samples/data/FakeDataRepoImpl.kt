package com.pinslog.samples.data

import android.util.Log
import com.pinslog.samples.view.LOG_TAG
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.random.Random

class FakeDataRepoImpl: FakeDataRepo {
    override fun getFakeData(isContinue: Boolean): Flow<Int?> {
        Log.d(LOG_TAG, "isContinue: $isContinue")
        return flow {
            if (isContinue) {
                while (true) {
                    emit(Random.nextInt(-50, 50))
                    delay(100)
                }
            } else {
                emit(null)
            }
        }
    }
}