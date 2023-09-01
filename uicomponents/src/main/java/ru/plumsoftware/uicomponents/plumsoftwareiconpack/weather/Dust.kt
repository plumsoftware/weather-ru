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

public val WeatherGroup.Dust: ImageVector
    get() {
        if (_dust != null) {
            return _dust!!
        }
        _dust = Builder(name = "Dust", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
                viewportWidth = 24.0f, viewportHeight = 24.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = EvenOdd) {
                moveTo(11.2908f, 3.5418f)
                curveTo(11.378f, 2.757f, 12.5187f, 2.7571f, 12.6059f, 3.5418f)
                lineTo(12.689f, 4.2893f)
                curveTo(13.0995f, 7.9839f, 16.0161f, 10.9005f, 19.7106f, 11.311f)
                lineTo(20.4582f, 11.394f)
                curveTo(21.2429f, 11.4812f, 21.2429f, 12.622f, 20.4582f, 12.7092f)
                lineTo(19.7106f, 12.7922f)
                curveTo(16.0161f, 13.2028f, 13.0995f, 16.1193f, 12.689f, 19.8139f)
                lineTo(12.6059f, 20.5615f)
                curveTo(12.5187f, 21.3462f, 11.378f, 21.3462f, 11.2908f, 20.5615f)
                lineTo(11.2077f, 19.8139f)
                curveTo(10.7972f, 16.1193f, 7.8807f, 13.2028f, 4.1861f, 12.7922f)
                lineTo(3.4385f, 12.7092f)
                curveTo(2.6538f, 12.622f, 2.6538f, 11.4812f, 3.4385f, 11.394f)
                lineTo(4.1861f, 11.311f)
                curveTo(7.8807f, 10.9005f, 10.7972f, 7.9839f, 11.2077f, 4.2893f)
                lineTo(11.2908f, 3.5418f)
                close()
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = EvenOdd) {
                moveTo(5.279f, 14.7913f)
                curveTo(5.51f, 14.0984f, 6.49f, 14.0984f, 6.721f, 14.7913f)
                lineTo(6.7372f, 14.8398f)
                curveTo(6.9611f, 15.5117f, 7.4883f, 16.0389f, 8.1602f, 16.2628f)
                lineTo(8.2087f, 16.279f)
                curveTo(8.9016f, 16.51f, 8.9016f, 17.49f, 8.2087f, 17.721f)
                lineTo(8.1602f, 17.7372f)
                curveTo(7.4883f, 17.9611f, 6.9611f, 18.4883f, 6.7372f, 19.1602f)
                lineTo(6.721f, 19.2087f)
                curveTo(6.49f, 19.9016f, 5.51f, 19.9016f, 5.279f, 19.2087f)
                lineTo(5.2628f, 19.1602f)
                curveTo(5.0389f, 18.4883f, 4.5117f, 17.9611f, 3.8398f, 17.7372f)
                lineTo(3.7913f, 17.721f)
                curveTo(3.0984f, 17.4901f, 3.0984f, 16.51f, 3.7913f, 16.279f)
                lineTo(3.8398f, 16.2628f)
                curveTo(4.5117f, 16.0389f, 5.0389f, 15.5117f, 5.2628f, 14.8398f)
                lineTo(5.279f, 14.7913f)
                close()
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = EvenOdd) {
                moveTo(17.3227f, 3.3749f)
                curveTo(17.4627f, 2.675f, 18.4634f, 2.675f, 18.6034f, 3.3749f)
                lineTo(18.6876f, 3.7958f)
                curveTo(18.8406f, 4.5611f, 19.4388f, 5.1594f, 20.2042f, 5.3124f)
                lineTo(20.625f, 5.3966f)
                curveTo(21.3249f, 5.5366f, 21.325f, 6.5373f, 20.625f, 6.6773f)
                lineTo(20.2042f, 6.7614f)
                curveTo(19.4388f, 6.9145f, 18.8406f, 7.5127f, 18.6876f, 8.278f)
                lineTo(18.6034f, 8.6989f)
                curveTo(18.4634f, 9.3988f, 17.4627f, 9.3989f, 17.3227f, 8.6989f)
                lineTo(17.2385f, 8.278f)
                curveTo(17.0855f, 7.5127f, 16.4873f, 6.9145f, 15.7219f, 6.7614f)
                lineTo(15.3011f, 6.6773f)
                curveTo(14.6012f, 6.5373f, 14.6011f, 5.5366f, 15.3011f, 5.3966f)
                lineTo(15.7219f, 5.3124f)
                curveTo(16.4873f, 5.1594f, 17.0855f, 4.5611f, 17.2385f, 3.7958f)
                lineTo(17.3227f, 3.3749f)
                close()
            }
        }
        .build()
        return _dust!!
    }

private var _dust: ImageVector? = null
