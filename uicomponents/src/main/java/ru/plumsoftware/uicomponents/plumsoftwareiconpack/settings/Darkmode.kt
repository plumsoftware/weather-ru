package ru.plumsoftware.uicomponents.plumsoftwareiconpack.settings

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import ru.plumsoftware.uicomponents.plumsoftwareiconpack.SettingsGroup

public val SettingsGroup.Darkmode: ImageVector
    get() {
        if (_darkmode != null) {
            return _darkmode!!
        }
        _darkmode = Builder(name = "Darkmode", defaultWidth = 16.0.dp, defaultHeight = 17.0.dp,
                viewportWidth = 16.0f, viewportHeight = 17.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(6.0003f, 0.7783f)
                curveTo(6.1825f, 1.0004f, 6.2447f, 1.3352f, 6.0807f, 1.6361f)
                curveTo(5.5201f, 2.6647f, 5.2015f, 3.843f, 5.2015f, 5.0969f)
                curveTo(5.2015f, 9.1173f, 8.4803f, 12.3727f, 12.5202f, 12.3727f)
                curveTo(13.0466f, 12.3727f, 13.5594f, 12.3175f, 14.0534f, 12.2129f)
                curveTo(14.3912f, 12.1413f, 14.6984f, 12.294f, 14.8631f, 12.5286f)
                curveTo(15.033f, 12.7707f, 15.0686f, 13.1318f, 14.8318f, 13.4224f)
                curveTo(13.3034f, 15.2985f, 10.9648f, 16.5f, 8.3436f, 16.5f)
                curveTo(3.7334f, 16.5f, 0.0f, 12.7863f, 0.0f, 8.21f)
                curveTo(0.0f, 4.7658f, 2.1142f, 1.812f, 5.1235f, 0.5601f)
                curveTo(5.4712f, 0.4154f, 5.8123f, 0.5491f, 6.0003f, 0.7783f)
                close()
            }
        }
        .build()
        return _darkmode!!
    }

private var _darkmode: ImageVector? = null
