package com.evanisnor.flickwatcher.network

import android.net.ConnectivityManager
import android.net.Network
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@NetworkScope
class NetworkMonitor @Inject constructor(
    private val dispatcher: CoroutineDispatcher,
    private val connectivityManager: ConnectivityManager
) {

    enum class Status {
        Unknown,
        Connected,
        Disconnected
    }

    private val networkStatus = MutableStateFlow(Status.Unknown)

    suspend fun networkState() = callbackFlow<Status> {
        trySend(networkStatus.value)

        val callback = object : ConnectivityManager.NetworkCallback() {

            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                networkStatus.value = Status.Connected
                trySend(networkStatus.value)
            }

            override fun onLost(network: Network) {
                super.onLost(network)
                networkStatus.value = Status.Disconnected
                trySend(networkStatus.value)
            }

            override fun onUnavailable() {
                super.onUnavailable()
                networkStatus.value = Status.Disconnected
                trySend(networkStatus.value)
            }
        }

        connectivityManager.registerDefaultNetworkCallback(callback)

        awaitClose {
            connectivityManager.unregisterNetworkCallback(callback)
        }
    }.flowOn(dispatcher)
}