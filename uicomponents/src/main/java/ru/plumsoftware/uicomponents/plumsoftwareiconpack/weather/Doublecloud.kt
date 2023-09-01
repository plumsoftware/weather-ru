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

public val WeatherGroup.Doublecloud: ImageVector
    get() {
        if (_doublecloud != null) {
            return _doublecloud!!
        }
        _doublecloud = Builder(name = "Doublecloud", defaultWidth = 24.0.dp, defaultHeight =
                24.0.dp, viewportWidth = 24.0f, viewportHeight = 24.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = EvenOdd) {
                moveTo(19.9059f, 10.3537f)
                curveTo(20.3155f, 8.7661f, 20.0001f, 7.3262f, 19.2235f, 6.1773f)
                curveTo(18.3696f, 4.914f, 16.9891f, 4.0426f, 15.5055f, 3.6532f)
                curveTo(14.0193f, 3.2631f, 12.3655f, 3.3396f, 10.9389f, 4.057f)
                curveTo(9.565f, 4.7479f, 8.4454f, 6.013f, 7.9066f, 7.9123f)
                curveTo(5.7531f, 8.4117f, 4.3972f, 9.907f, 3.9837f, 11.6072f)
                curveTo(4.5021f, 10.7113f, 5.2172f, 10.0599f, 6.0407f, 9.6443f)
                curveTo(7.2702f, 9.0239f, 8.6818f, 8.9573f, 9.9446f, 9.2822f)
                curveTo(11.2045f, 9.6064f, 12.3936f, 10.3406f, 13.1507f, 11.4253f)
                curveTo(13.8156f, 12.3781f, 14.1206f, 13.5685f, 13.8688f, 14.8768f)
                curveTo(14.781f, 15.1567f, 15.4544f, 15.8153f, 15.819f, 16.6113f)
                curveTo(15.9566f, 16.9116f, 16.0513f, 17.2344f, 16.099f, 17.5659f)
                curveTo(16.887f, 17.576f, 17.6095f, 17.584f, 18.1712f, 17.5842f)
                curveTo(19.5942f, 17.6649f, 20.7147f, 17.3163f, 21.504f, 16.6596f)
                curveTo(22.2982f, 15.9986f, 22.6962f, 15.0744f, 22.7449f, 14.1432f)
                curveTo(22.8324f, 12.4723f, 21.7861f, 10.7289f, 19.9059f, 10.3537f)
                close()
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = EvenOdd) {
                moveTo(12.8225f, 14.9453f)
                curveTo(13.0336f, 13.8886f, 12.7779f, 12.9271f, 12.2203f, 12.1576f)
                curveTo(11.5854f, 11.2815f, 10.5882f, 10.6885f, 9.5317f, 10.4266f)
                curveTo(8.4727f, 10.1642f, 7.289f, 10.218f, 6.2579f, 10.7191f)
                curveTo(5.2835f, 11.1927f, 4.4901f, 12.0454f, 4.0856f, 13.2973f)
                curveTo(2.0895f, 13.8136f, 1.0589f, 15.5052f, 1.2793f, 17.1463f)
                curveTo(1.3942f, 18.0009f, 1.8486f, 18.8102f, 2.6332f, 19.3781f)
                curveTo(3.4161f, 19.9448f, 4.4839f, 20.2415f, 5.7916f, 20.1704f)
                curveTo(6.5083f, 20.1315f, 8.072f, 20.1514f, 9.4704f, 20.1693f)
                curveTo(10.1705f, 20.1782f, 10.8292f, 20.1866f, 11.3195f, 20.1869f)
                curveTo(12.6889f, 20.2603f, 13.7151f, 19.7942f, 14.2787f, 18.9714f)
                curveTo(14.8281f, 18.1693f, 14.8433f, 17.1565f, 14.4579f, 16.3462f)
                curveTo(14.1522f, 15.7033f, 13.5874f, 15.1713f, 12.8225f, 14.9453f)
                close()
            }
        }
        .build()
        return _doublecloud!!
    }

private var _doublecloud: ImageVector? = null
