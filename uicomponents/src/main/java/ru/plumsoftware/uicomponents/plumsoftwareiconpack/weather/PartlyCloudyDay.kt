package ru.plumsoftware.uicomponents.plumsoftwareiconpack.weather

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.PathFillType.Companion.EvenOdd
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import ru.plumsoftware.uicomponents.plumsoftwareiconpack.WeatherGroup

public val WeatherGroup.PartlyCloudyDay: ImageVector
    get() {
        if (_partlyCloudyDay != null) {
            return _partlyCloudyDay!!
        }
        _partlyCloudyDay = Builder(name = "PartlyCloudyDay", defaultWidth = 24.0.dp, defaultHeight =
                24.0.dp, viewportWidth = 24.0f, viewportHeight = 24.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = EvenOdd) {
                moveTo(20.2234f, 10.4739f)
                curveTo(21.0045f, 11.5615f, 21.3352f, 12.9243f, 20.9603f, 14.4249f)
                curveTo(22.8182f, 14.8304f, 23.8519f, 16.5036f, 23.7541f, 18.1114f)
                curveTo(23.699f, 19.0174f, 23.2873f, 19.9136f, 22.4755f, 20.5522f)
                curveTo(21.6703f, 21.1855f, 20.538f, 21.5152f, 19.1157f, 21.4389f)
                curveTo(18.4152f, 21.4386f, 17.4661f, 21.4268f, 16.4535f, 21.4141f)
                curveTo(14.4211f, 21.3886f, 12.133f, 21.36f, 11.0856f, 21.4159f)
                curveTo(9.2384f, 21.5146f, 7.7569f, 21.1005f, 6.6843f, 20.3341f)
                curveTo(5.6092f, 19.5658f, 4.9895f, 18.4727f, 4.8374f, 17.3179f)
                curveTo(4.5418f, 15.0747f, 6.0073f, 12.7623f, 8.851f, 12.1095f)
                curveTo(9.4012f, 10.3168f, 10.5241f, 9.1174f, 11.8982f, 8.4601f)
                curveTo(13.3328f, 7.774f, 14.991f, 7.7006f, 16.4796f, 8.0696f)
                curveTo(17.9652f, 8.4378f, 19.3553f, 9.2654f, 20.2234f, 10.4739f)
                close()
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = EvenOdd) {
                moveTo(4.6754f, 2.9197f)
                curveTo(5.0604f, 2.7667f, 5.4964f, 2.9548f, 5.6494f, 3.3397f)
                lineTo(6.0822f, 4.4292f)
                curveTo(6.2352f, 4.8141f, 6.0471f, 5.2502f, 5.6622f, 5.4031f)
                curveTo(5.2772f, 5.5561f, 4.8412f, 5.368f, 4.6883f, 4.9831f)
                lineTo(4.2554f, 3.8936f)
                curveTo(4.1024f, 3.5086f, 4.2905f, 3.0726f, 4.6754f, 2.9197f)
                close()
                moveTo(10.3588f, 3.0518f)
                curveTo(10.7401f, 3.2137f, 10.9179f, 3.654f, 10.756f, 4.0353f)
                lineTo(10.3231f, 5.0546f)
                curveTo(10.1612f, 5.4359f, 9.7209f, 5.6137f, 9.3396f, 5.4518f)
                curveTo(8.9584f, 5.2899f, 8.7805f, 4.8495f, 8.9424f, 4.4683f)
                lineTo(9.3753f, 3.449f)
                curveTo(9.5372f, 3.0677f, 9.9776f, 2.8899f, 10.3588f, 3.0518f)
                close()
                moveTo(5.9205f, 6.0638f)
                curveTo(7.4879f, 5.4317f, 9.2104f, 5.8416f, 10.3366f, 6.9603f)
                lineTo(10.9981f, 7.6175f)
                lineTo(10.1596f, 8.1581f)
                curveTo(9.2521f, 8.7431f, 8.6012f, 9.6518f, 8.3393f, 10.6993f)
                lineTo(8.2191f, 11.1804f)
                lineTo(7.6709f, 11.3632f)
                curveTo(6.8798f, 11.6268f, 6.1495f, 12.0462f, 5.523f, 12.5964f)
                lineTo(4.8472f, 13.1899f)
                lineTo(4.3528f, 12.619f)
                curveTo(4.0562f, 12.2765f, 3.8115f, 11.8794f, 3.6355f, 11.4362f)
                curveTo(2.795f, 9.321f, 3.8122f, 6.9142f, 5.9205f, 6.0638f)
                close()
                moveTo(0.649f, 6.9651f)
                curveTo(0.8132f, 6.5848f, 1.2546f, 6.4096f, 1.6348f, 6.5739f)
                lineTo(2.6463f, 7.0107f)
                curveTo(3.0265f, 7.1749f, 3.2017f, 7.6163f, 3.0375f, 7.9965f)
                curveTo(2.8732f, 8.3768f, 2.4318f, 8.552f, 2.0516f, 8.3877f)
                lineTo(1.0401f, 7.9509f)
                curveTo(0.6599f, 7.7867f, 0.4847f, 7.3453f, 0.649f, 6.9651f)
                close()
                moveTo(2.9499f, 11.7169f)
                curveTo(3.1049f, 12.101f, 2.919f, 12.538f, 2.5349f, 12.693f)
                lineTo(1.4958f, 13.112f)
                curveTo(1.1117f, 13.267f, 0.6747f, 13.0812f, 0.5197f, 12.697f)
                curveTo(0.3648f, 12.3129f, 0.5506f, 11.8759f, 0.9348f, 11.7209f)
                lineTo(1.9738f, 11.3018f)
                curveTo(2.358f, 11.1469f, 2.795f, 11.3327f, 2.9499f, 11.7169f)
                close()
            }
        }
        .build()
        return _partlyCloudyDay!!
    }

private var _partlyCloudyDay: ImageVector? = null
