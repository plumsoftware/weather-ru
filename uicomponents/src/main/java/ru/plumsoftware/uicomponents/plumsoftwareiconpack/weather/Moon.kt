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

public val WeatherGroup.Moon: ImageVector
    get() {
        if (_moon != null) {
            return _moon!!
        }
        _moon = Builder(name = "Moon", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
                viewportWidth = 24.0f, viewportHeight = 24.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = EvenOdd) {
                moveTo(10.6875f, 3.074f)
                curveTo(11.2424f, 3.5792f, 11.114f, 4.3576f, 10.8493f, 4.946f)
                lineTo(10.8489f, 4.947f)
                curveTo(10.4504f, 5.829f, 10.2316f, 6.7949f, 10.2398f, 7.8161f)
                lineTo(10.2398f, 7.8184f)
                curveTo(10.256f, 11.6017f, 13.5099f, 14.8623f, 17.5362f, 15.0218f)
                curveTo(18.1294f, 15.0457f, 18.6969f, 15.0059f, 19.2474f, 14.9105f)
                curveTo(19.6039f, 14.8476f, 19.9479f, 14.8375f, 20.2543f, 14.9067f)
                curveTo(20.5644f, 14.9768f, 20.8776f, 15.1401f, 21.0739f, 15.4512f)
                curveTo(21.2715f, 15.7643f, 21.2822f, 16.1182f, 21.2061f, 16.4283f)
                curveTo(21.1315f, 16.7323f, 20.9673f, 17.0307f, 20.7524f, 17.3127f)
                curveTo(18.8656f, 19.8121f, 15.7316f, 21.3871f, 12.2417f, 21.2406f)
                curveTo(7.2929f, 21.0299f, 3.1216f, 17.192f, 2.7759f, 12.4105f)
                curveTo(2.4602f, 8.1722f, 5.0586f, 4.4967f, 8.8085f, 2.9655f)
                curveTo(9.4089f, 2.7193f, 10.1679f, 2.6009f, 10.6875f, 3.074f)
                close()
            }
        }
        .build()
        return _moon!!
    }

private var _moon: ImageVector? = null
