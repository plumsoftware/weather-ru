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

public val WeatherGroup.Sunny: ImageVector
    get() {
        if (_sunny != null) {
            return _sunny!!
        }
        _sunny = Builder(name = "Sunny", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
                viewportWidth = 24.0f, viewportHeight = 24.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = EvenOdd) {
                moveTo(12.0f, 1.25f)
                curveTo(12.4142f, 1.25f, 12.75f, 1.5858f, 12.75f, 2.0f)
                verticalLineTo(3.7334f)
                curveTo(12.75f, 4.1476f, 12.4142f, 4.4833f, 12.0f, 4.4833f)
                curveTo(11.5858f, 4.4833f, 11.25f, 4.1476f, 11.25f, 3.7334f)
                verticalLineTo(2.0f)
                curveTo(11.25f, 1.5858f, 11.5858f, 1.25f, 12.0f, 1.25f)
                close()
                moveTo(19.5303f, 4.4697f)
                curveTo(19.8232f, 4.7626f, 19.8232f, 5.2375f, 19.5303f, 5.5303f)
                lineTo(18.372f, 6.6887f)
                curveTo(18.0791f, 6.9816f, 17.6042f, 6.9816f, 17.3113f, 6.6887f)
                curveTo(17.0184f, 6.3958f, 17.0184f, 5.9209f, 17.3113f, 5.628f)
                lineTo(18.4697f, 4.4697f)
                curveTo(18.7626f, 4.1768f, 19.2374f, 4.1768f, 19.5303f, 4.4697f)
                close()
                moveTo(4.4697f, 4.4697f)
                curveTo(4.7626f, 4.1768f, 5.2374f, 4.1768f, 5.5303f, 4.4697f)
                lineTo(6.6887f, 5.628f)
                curveTo(6.9816f, 5.9209f, 6.9816f, 6.3958f, 6.6887f, 6.6887f)
                curveTo(6.3958f, 6.9816f, 5.9209f, 6.9816f, 5.628f, 6.6887f)
                lineTo(4.4697f, 5.5303f)
                curveTo(4.1768f, 5.2375f, 4.1768f, 4.7626f, 4.4697f, 4.4697f)
                close()
                moveTo(1.25f, 12.0f)
                curveTo(1.25f, 11.5858f, 1.5858f, 11.25f, 2.0f, 11.25f)
                horizontalLineTo(3.6667f)
                curveTo(4.0809f, 11.25f, 4.4167f, 11.5858f, 4.4167f, 12.0f)
                curveTo(4.4167f, 12.4142f, 4.0809f, 12.75f, 3.6667f, 12.75f)
                horizontalLineTo(2.0f)
                curveTo(1.5858f, 12.75f, 1.25f, 12.4142f, 1.25f, 12.0f)
                close()
                moveTo(19.5833f, 12.0f)
                curveTo(19.5833f, 11.5858f, 19.9191f, 11.25f, 20.3333f, 11.25f)
                horizontalLineTo(22.0f)
                curveTo(22.4142f, 11.25f, 22.75f, 11.5858f, 22.75f, 12.0f)
                curveTo(22.75f, 12.4142f, 22.4142f, 12.75f, 22.0f, 12.75f)
                horizontalLineTo(20.3333f)
                curveTo(19.9191f, 12.75f, 19.5833f, 12.4142f, 19.5833f, 12.0f)
                close()
                moveTo(6.6887f, 17.3113f)
                curveTo(6.9816f, 17.6042f, 6.9816f, 18.0791f, 6.6887f, 18.372f)
                lineTo(5.447f, 19.6137f)
                curveTo(5.1541f, 19.9066f, 4.6792f, 19.9066f, 4.3863f, 19.6137f)
                curveTo(4.0934f, 19.3208f, 4.0934f, 18.8459f, 4.3863f, 18.553f)
                lineTo(5.628f, 17.3114f)
                curveTo(5.9209f, 17.0185f, 6.3958f, 17.0185f, 6.6887f, 17.3113f)
                close()
                moveTo(17.3113f, 17.3113f)
                curveTo(17.6042f, 17.0185f, 18.0791f, 17.0185f, 18.372f, 17.3114f)
                lineTo(19.5303f, 18.4697f)
                curveTo(19.8232f, 18.7626f, 19.8232f, 19.2374f, 19.5303f, 19.5303f)
                curveTo(19.2374f, 19.8232f, 18.7625f, 19.8232f, 18.4697f, 19.5303f)
                lineTo(17.3113f, 18.372f)
                curveTo(17.0184f, 18.0791f, 17.0184f, 17.6042f, 17.3113f, 17.3113f)
                close()
                moveTo(12.0f, 19.5833f)
                curveTo(12.4142f, 19.5833f, 12.75f, 19.9191f, 12.75f, 20.3333f)
                verticalLineTo(22.0f)
                curveTo(12.75f, 22.4142f, 12.4142f, 22.75f, 12.0f, 22.75f)
                curveTo(11.5858f, 22.75f, 11.25f, 22.4142f, 11.25f, 22.0f)
                verticalLineTo(20.3333f)
                curveTo(11.25f, 19.9191f, 11.5858f, 19.5833f, 12.0f, 19.5833f)
                close()
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = EvenOdd) {
                moveTo(6.25f, 12.0f)
                curveTo(6.25f, 8.8244f, 8.8244f, 6.25f, 12.0f, 6.25f)
                curveTo(15.1756f, 6.25f, 17.75f, 8.8244f, 17.75f, 12.0f)
                curveTo(17.75f, 15.1756f, 15.1756f, 17.75f, 12.0f, 17.75f)
                curveTo(8.8244f, 17.75f, 6.25f, 15.1756f, 6.25f, 12.0f)
                close()
            }
        }
        .build()
        return _sunny!!
    }

private var _sunny: ImageVector? = null
