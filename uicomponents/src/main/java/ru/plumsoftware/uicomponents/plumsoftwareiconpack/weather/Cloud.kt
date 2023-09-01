package ru.plumsoftware.uicomponents.plumsoftwareiconpack.weather

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.EvenOdd
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import ru.plumsoftware.uicomponents.plumsoftwareiconpack.WeatherGroup

public val WeatherGroup.Cloud: ImageVector
    get() {
        if (_cloud != null) {
            return _cloud!!
        }
        _cloud = Builder(name = "Cloud", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
                viewportWidth = 24.0f, viewportHeight = 24.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = EvenOdd) {
                moveTo(18.7386f, 7.2648f)
                curveTo(19.622f, 8.5211f, 19.9807f, 10.0955f, 19.5148f, 11.8314f)
                curveTo(21.6536f, 12.2417f, 22.8437f, 14.148f, 22.7442f, 15.975f)
                curveTo(22.6888f, 16.9931f, 22.2361f, 18.0038f, 21.3326f, 18.7264f)
                curveTo(20.4349f, 19.4445f, 19.1602f, 19.8257f, 17.5415f, 19.7374f)
                curveTo(16.7424f, 19.7372f, 15.6569f, 19.7235f, 14.4975f, 19.7088f)
                curveTo(12.1672f, 19.6794f, 9.5382f, 19.6462f, 8.3353f, 19.711f)
                curveTo(6.2347f, 19.8244f, 4.561f, 19.3476f, 3.356f, 18.4763f)
                curveTo(2.149f, 17.6035f, 1.4584f, 16.3657f, 1.2907f, 15.0626f)
                curveTo(0.964f, 12.5252f, 2.6168f, 9.8861f, 5.8654f, 9.162f)
                curveTo(6.4784f, 7.0852f, 7.7519f, 5.702f, 9.3147f, 4.9465f)
                curveTo(10.9376f, 4.162f, 12.8188f, 4.0784f, 14.5093f, 4.5049f)
                curveTo(16.197f, 4.9307f, 17.7673f, 5.8836f, 18.7386f, 7.2648f)
                close()
            }
        }
        .build()
        return _cloud!!
    }

private var _cloud: ImageVector? = null
