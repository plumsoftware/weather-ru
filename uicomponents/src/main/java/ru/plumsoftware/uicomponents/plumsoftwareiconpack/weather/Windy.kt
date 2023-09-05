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

public val WeatherGroup.Windy: ImageVector
    get() {
        if (_windy != null) {
            return _windy!!
        }
        _windy = Builder(name = "Windy", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
                viewportWidth = 24.0f, viewportHeight = 24.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = EvenOdd) {
                moveTo(15.8019f, 10.1519f)
                curveTo(15.8019f, 8.6108f, 16.9857f, 7.2783f, 18.5428f, 7.2783f)
                curveTo(20.0892f, 7.2783f, 21.2838f, 8.5993f, 21.2838f, 10.1519f)
                curveTo(21.2838f, 11.6929f, 20.1f, 13.0254f, 18.5428f, 13.0254f)
                horizontalLineTo(14.4527f)
                curveTo(14.0833f, 13.0254f, 13.7838f, 12.7259f, 13.7838f, 12.3565f)
                curveTo(13.7838f, 11.9871f, 14.0833f, 11.6876f, 14.4527f, 11.6876f)
                horizontalLineTo(15.8019f)
                verticalLineTo(10.1519f)
                close()
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = EvenOdd) {
                moveTo(8.6037f, 7.3374f)
                curveTo(8.6037f, 5.4574f, 9.9944f, 3.8f, 11.8626f, 3.8f)
                curveTo(13.7182f, 3.8f, 15.1216f, 5.4433f, 15.1216f, 7.3374f)
                curveTo(15.1216f, 9.2175f, 13.7308f, 10.8749f, 11.8626f, 10.8749f)
                horizontalLineTo(5.9797f)
                curveTo(5.6103f, 10.8749f, 5.3108f, 10.5754f, 5.3108f, 10.2059f)
                curveTo(5.3108f, 9.8365f, 5.6103f, 9.537f, 5.9797f, 9.537f)
                horizontalLineTo(8.6037f)
                verticalLineTo(7.3374f)
                close()
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = EvenOdd) {
                moveTo(8.6037f, 16.9626f)
                curveTo(8.6037f, 18.8426f, 9.9944f, 20.5f, 11.8626f, 20.5f)
                curveTo(13.7182f, 20.5f, 15.1216f, 18.8567f, 15.1216f, 16.9626f)
                curveTo(15.1216f, 15.0825f, 13.7308f, 13.4252f, 11.8626f, 13.4252f)
                horizontalLineTo(3.6689f)
                curveTo(3.2995f, 13.4252f, 3.0f, 13.7246f, 3.0f, 14.0941f)
                curveTo(3.0f, 14.4635f, 3.2995f, 14.763f, 3.6689f, 14.763f)
                horizontalLineTo(8.6037f)
                verticalLineTo(16.9626f)
                close()
            }
        }
        .build()
        return _windy!!
    }

private var _windy: ImageVector? = null
