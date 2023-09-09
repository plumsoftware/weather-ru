package ru.plumsoftware.uicomponents.plumsoftwareiconpack.widget

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import ru.plumsoftware.uicomponents.plumsoftwareiconpack.WidgetGroup

public val WidgetGroup.WidgetBack: ImageVector
    get() {
        if (_widgetBack != null) {
            return _widgetBack!!
        }
        _widgetBack = Builder(name = "WidgetBack", defaultWidth = 116.0.dp, defaultHeight = 95.0.dp,
                viewportWidth = 116.0f, viewportHeight = 95.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(69.287f, 94.621f)
                horizontalLineTo(32.258f)
                curveTo(9.356f, 94.621f, -6.132f, 71.264f, 2.781f, 50.167f)
                lineTo(15.718f, 19.546f)
                curveTo(20.723f, 7.699f, 32.334f, 0.0f, 45.195f, 0.0f)
                horizontalLineTo(83.648f)
                curveTo(107.086f, 0.0f, 122.572f, 24.367f, 112.62f, 45.588f)
                lineTo(98.259f, 76.209f)
                curveTo(92.989f, 87.445f, 81.698f, 94.621f, 69.287f, 94.621f)
                close()
            }
        }
        .build()
        return _widgetBack!!
    }

private var _widgetBack: ImageVector? = null
