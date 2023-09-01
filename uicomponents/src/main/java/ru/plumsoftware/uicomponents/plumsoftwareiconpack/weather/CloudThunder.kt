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

public val WeatherGroup.CloudThunder: ImageVector
    get() {
        if (_cloudThunder != null) {
            return _cloudThunder!!
        }
        _cloudThunder = Builder(name = "CloudThunder", defaultWidth = 24.0.dp, defaultHeight =
                24.0.dp, viewportWidth = 24.0f, viewportHeight = 24.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = EvenOdd) {
                moveTo(19.1652f, 8.9788f)
                curveTo(19.6094f, 7.3236f, 19.2674f, 5.8225f, 18.425f, 4.6246f)
                curveTo(17.4989f, 3.3076f, 16.0016f, 2.399f, 14.3925f, 1.9931f)
                curveTo(12.7805f, 1.5864f, 10.9868f, 1.6661f, 9.4395f, 2.4141f)
                curveTo(7.9493f, 3.1344f, 6.735f, 4.4533f, 6.1506f, 6.4335f)
                curveTo(3.2126f, 7.0883f, 1.6412f, 9.3855f, 1.7559f, 11.6929f)
                curveTo(1.8764f, 14.1181f, 3.8325f, 16.2875f, 7.3697f, 16.4916f)
                lineTo(7.3896f, 16.4928f)
                lineTo(8.942f, 16.4956f)
                curveTo(8.985f, 16.3262f, 9.0634f, 16.16f, 9.1832f, 16.008f)
                lineTo(9.1866f, 16.0038f)
                lineTo(12.5912f, 11.7498f)
                lineTo(12.5919f, 11.7489f)
                curveTo(12.9588f, 11.2885f, 13.5312f, 11.1839f, 13.9974f, 11.3774f)
                curveTo(14.4524f, 11.5661f, 14.7748f, 12.0198f, 14.7748f, 12.5711f)
                verticalLineTo(15.4188f)
                horizontalLineTo(15.4499f)
                curveTo(16.018f, 15.4188f, 16.4197f, 15.7914f, 16.5869f, 16.1974f)
                curveTo(16.6274f, 16.2958f, 16.6566f, 16.4009f, 16.6729f, 16.5096f)
                lineTo(18.209f, 16.5123f)
                lineTo(18.2358f, 16.5104f)
                curveTo(19.5685f, 16.4125f, 20.5967f, 15.9315f, 21.2867f, 15.1942f)
                curveTo(21.9734f, 14.4603f, 22.2754f, 13.5243f, 22.2483f, 12.6115f)
                curveTo(22.1998f, 10.973f, 21.0793f, 9.3465f, 19.1652f, 8.9788f)
                close()
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = EvenOdd) {
                moveTo(13.4118f, 13.15f)
                lineTo(10.3999f, 16.9133f)
                horizontalLineTo(11.5051f)
                curveTo(11.9193f, 16.9133f, 12.2551f, 17.249f, 12.2551f, 17.6633f)
                verticalLineTo(20.8479f)
                lineTo(15.267f, 17.0847f)
                horizontalLineTo(14.1618f)
                curveTo(13.7476f, 17.0847f, 13.4118f, 16.7489f, 13.4118f, 16.3347f)
                verticalLineTo(13.15f)
                close()
            }
        }
        .build()
        return _cloudThunder!!
    }

private var _cloudThunder: ImageVector? = null
