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

public val WeatherGroup.Mild: ImageVector
    get() {
        if (_mild != null) {
            return _mild!!
        }
        _mild = Builder(name = "Mild", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
                viewportWidth = 24.0f, viewportHeight = 24.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = EvenOdd) {
                moveTo(9.1673f, 4.9432f)
                curveTo(9.1673f, 3.3405f, 10.4445f, 2.0153f, 12.0517f, 2.0153f)
                curveTo(13.6589f, 2.0153f, 14.9362f, 3.3405f, 14.9362f, 4.9432f)
                verticalLineTo(15.2651f)
                curveTo(15.3996f, 15.7681f, 15.7285f, 16.3847f, 15.8901f, 17.0558f)
                curveTo(16.08f, 17.8447f, 16.0303f, 18.6738f, 15.7471f, 19.4337f)
                curveTo(15.4639f, 20.1938f, 14.9598f, 20.8504f, 14.2992f, 21.3147f)
                curveTo(13.6384f, 21.7792f, 12.8531f, 22.0285f, 12.0475f, 22.0277f)
                curveTo(11.242f, 22.0268f, 10.4571f, 21.7758f, 9.7973f, 21.3099f)
                curveTo(9.1377f, 20.8442f, 8.635f, 20.1865f, 8.3533f, 19.4259f)
                curveTo(8.0717f, 18.6654f, 8.0237f, 17.8361f, 8.2153f, 17.0477f)
                curveTo(8.3776f, 16.3796f, 8.7058f, 15.766f, 9.1673f, 15.2651f)
                verticalLineTo(4.9432f)
                close()
                moveTo(12.7326f, 9.9388f)
                curveTo(12.7326f, 9.5554f, 12.428f, 9.2446f, 12.0522f, 9.2446f)
                curveTo(11.6765f, 9.2446f, 11.3719f, 9.5554f, 11.3719f, 9.9388f)
                verticalLineTo(17.0667f)
                curveTo(10.9651f, 17.3068f, 10.6915f, 17.7555f, 10.6915f, 18.2694f)
                curveTo(10.6915f, 19.0362f, 11.3007f, 19.6578f, 12.0522f, 19.6578f)
                curveTo(12.8037f, 19.6578f, 13.4129f, 19.0362f, 13.4129f, 18.2694f)
                curveTo(13.4129f, 17.7555f, 13.1393f, 17.3068f, 12.7326f, 17.0667f)
                verticalLineTo(9.9388f)
                close()
            }
        }
        .build()
        return _mild!!
    }

private var _mild: ImageVector? = null
