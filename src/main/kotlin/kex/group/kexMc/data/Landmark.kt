package kex.group.kexMc.data

import kotlinx.serialization.Serializable

@Serializable
data class Landmark(val name: String?, val x: Double, val y: Double, val z: Double)