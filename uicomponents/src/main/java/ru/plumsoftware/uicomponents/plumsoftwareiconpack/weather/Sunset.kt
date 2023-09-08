package ru.plumsoftware.uicomponents.plumsoftwareiconpack.weather

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.PathFillType.Companion.EvenOdd
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
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

public val WeatherGroup.Sunset: ImageVector
    get() {
        if (_sunset != null) {
            return _sunset!!
        }
        _sunset = Builder(name = "Sunset", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
                viewportWidth = 24.0f, viewportHeight = 24.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(15.8891f, 9.8909f)
                curveTo(16.9205f, 10.9224f, 17.5f, 12.3213f, 17.5f, 13.78f)
                lineTo(6.5f, 13.78f)
                curveTo(6.5f, 12.3213f, 7.0795f, 10.9224f, 8.1109f, 9.8909f)
                curveTo(9.1424f, 8.8595f, 10.5413f, 8.28f, 12.0f, 8.28f)
                curveTo(13.4587f, 8.28f, 14.8576f, 8.8595f, 15.8891f, 9.8909f)
                close()
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = EvenOdd) {
                moveTo(7.5806f, 9.3606f)
                curveTo(8.7527f, 8.1885f, 10.3424f, 7.53f, 12.0f, 7.53f)
                curveTo(13.6576f, 7.53f, 15.2473f, 8.1885f, 16.4194f, 9.3606f)
                curveTo(17.5915f, 10.5327f, 18.25f, 12.1224f, 18.25f, 13.78f)
                curveTo(18.25f, 14.1942f, 17.9142f, 14.53f, 17.5f, 14.53f)
                lineTo(6.5f, 14.53f)
                curveTo(6.0858f, 14.53f, 5.75f, 14.1942f, 5.75f, 13.78f)
                curveTo(5.75f, 12.1224f, 6.4085f, 10.5327f, 7.5806f, 9.3606f)
                close()
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = EvenOdd) {
                moveTo(12.0f, 2.03f)
                curveTo(12.4142f, 2.03f, 12.75f, 2.3658f, 12.75f, 2.78f)
                verticalLineTo(4.6867f)
                curveTo(12.75f, 5.1009f, 12.4142f, 5.4367f, 12.0f, 5.4367f)
                curveTo(11.5858f, 5.4367f, 11.25f, 5.1009f, 11.25f, 4.6867f)
                verticalLineTo(2.78f)
                curveTo(11.25f, 2.3658f, 11.5858f, 2.03f, 12.0f, 2.03f)
                close()
                moveTo(20.2303f, 5.5497f)
                curveTo(20.5232f, 5.8426f, 20.5232f, 6.3175f, 20.2303f, 6.6104f)
                lineTo(18.9562f, 7.8845f)
                curveTo(18.6633f, 8.1774f, 18.1884f, 8.1774f, 17.8955f, 7.8845f)
                curveTo(17.6026f, 7.5917f, 17.6026f, 7.1168f, 17.8955f, 6.8239f)
                lineTo(19.1697f, 5.5497f)
                curveTo(19.4626f, 5.2568f, 19.9374f, 5.2568f, 20.2303f, 5.5497f)
                close()
                moveTo(3.7697f, 5.5497f)
                curveTo(4.0626f, 5.2568f, 4.5374f, 5.2568f, 4.8303f, 5.5497f)
                lineTo(6.1045f, 6.8239f)
                curveTo(6.3974f, 7.1168f, 6.3974f, 7.5917f, 6.1045f, 7.8845f)
                curveTo(5.8116f, 8.1774f, 5.3367f, 8.1774f, 5.0438f, 7.8845f)
                lineTo(3.7697f, 6.6104f)
                curveTo(3.4768f, 6.3175f, 3.4768f, 5.8426f, 3.7697f, 5.5497f)
                close()
                moveTo(0.25f, 13.78f)
                curveTo(0.25f, 13.3658f, 0.5858f, 13.03f, 1.0f, 13.03f)
                horizontalLineTo(2.8333f)
                curveTo(3.2476f, 13.03f, 3.5833f, 13.3658f, 3.5833f, 13.78f)
                curveTo(3.5833f, 14.1943f, 3.2476f, 14.53f, 2.8333f, 14.53f)
                horizontalLineTo(1.0f)
                curveTo(0.5858f, 14.53f, 0.25f, 14.1943f, 0.25f, 13.78f)
                close()
                moveTo(20.4167f, 13.78f)
                curveTo(20.4167f, 13.3658f, 20.7525f, 13.03f, 21.1667f, 13.03f)
                horizontalLineTo(23.0f)
                curveTo(23.4142f, 13.03f, 23.75f, 13.3658f, 23.75f, 13.78f)
                curveTo(23.75f, 14.1943f, 23.4142f, 14.53f, 23.0f, 14.53f)
                horizontalLineTo(21.1667f)
                curveTo(20.7525f, 14.53f, 20.4167f, 14.1943f, 20.4167f, 13.78f)
                close()
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = EvenOdd) {
                moveTo(12.2771f, 14.68f)
                curveTo(12.6913f, 14.68f, 13.0271f, 15.0158f, 13.0271f, 15.43f)
                lineTo(13.0271f, 19.6245f)
                lineTo(14.2149f, 18.4386f)
                curveTo(14.5081f, 18.1459f, 14.9829f, 18.1463f, 15.2756f, 18.4394f)
                curveTo(15.5682f, 18.7326f, 15.5679f, 19.2074f, 15.2747f, 19.5001f)
                lineTo(13.1547f, 21.6166f)
                curveTo(12.6672f, 22.1033f, 11.8776f, 22.1033f, 11.3901f, 21.6166f)
                lineTo(9.2701f, 19.5001f)
                curveTo(8.977f, 19.2074f, 8.9766f, 18.7326f, 9.2692f, 18.4394f)
                curveTo(9.5619f, 18.1463f, 10.0367f, 18.1459f, 10.3299f, 18.4386f)
                lineTo(11.5271f, 19.6338f)
                lineTo(11.5271f, 15.43f)
                curveTo(11.5271f, 15.0158f, 11.8629f, 14.68f, 12.2771f, 14.68f)
                close()
            }
        }
        .build()
        return _sunset!!
    }

private var _sunset: ImageVector? = null
