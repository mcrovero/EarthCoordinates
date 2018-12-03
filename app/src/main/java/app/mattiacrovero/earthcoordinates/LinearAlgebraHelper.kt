package app.mattiacrovero.earthcoordinates

data class Vector3(var x: Float, var y: Float,var z : Float) {
    fun scale(factor: Float) : Vector3 {
        x *= factor
        y *= factor
        z *= factor
        return this
    }
    fun toFloatArray(): FloatArray {
        return floatArrayOf(x,y,z)
    }
    override fun toString(): String {
        return "x: $x, y: $y, z: $z"
    }
    override fun equals(other: Any?): Boolean {
        if(other is Vector3) {
            return other.x == x && other.y == y && other.z == z
        }
        return super.equals(other)
    }
    override fun hashCode(): Int {
        var result = x.hashCode()
        result = 31 * result + y.hashCode()
        result = 31 * result + z.hashCode()
        return result
    }
    companion object {
        fun fromFloatArray(arr: FloatArray) : Vector3 {
            return Vector3(arr[0],arr[1],arr[2])
        }
    }
}
class LinearAlgebraHelper {
    companion object {
        fun rotateVector3(vector : FloatArray, rotationMatrix: FloatArray): FloatArray {
            val newVector = FloatArray(3)
            for(i in 0..2) {
                var s = 0f
                for(j in 0..2) {
                    s += vector[j] * rotationMatrix[j+i*3]
                }
                newVector[i] = s
            }
            return newVector
        }
    }
}