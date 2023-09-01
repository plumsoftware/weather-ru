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

public val WeatherGroup.Hazzy: ImageVector
    get() {
        if (_hazzy != null) {
            return _hazzy!!
        }
        _hazzy = Builder(name = "Hazzy", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
                viewportWidth = 24.0f, viewportHeight = 24.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = EvenOdd) {
                moveTo(16.5999f, 12.0309f)
                curveTo(15.1751f, 11.6206f, 13.6447f, 11.8068f, 12.3598f, 12.5467f)
                curveTo(10.6238f, 13.5465f, 8.5363f, 13.7332f, 6.6505f, 13.0574f)
                lineTo(4.2649f, 12.2026f)
                curveTo(3.9113f, 12.0759f, 3.7273f, 11.6865f, 3.854f, 11.3328f)
                curveTo(3.9808f, 10.9792f, 4.3702f, 10.7952f, 4.7238f, 10.9219f)
                lineTo(7.1094f, 11.7767f)
                curveTo(8.6194f, 12.3178f, 10.2909f, 12.1683f, 11.6809f, 11.3678f)
                curveTo(13.2856f, 10.4437f, 15.1969f, 10.2111f, 16.9764f, 10.7235f)
                lineTo(19.194f, 11.3621f)
                curveTo(19.555f, 11.466f, 19.7634f, 11.843f, 19.6594f, 12.204f)
                curveTo(19.5555f, 12.565f, 19.1785f, 12.7734f, 18.8175f, 12.6694f)
                lineTo(16.5999f, 12.0309f)
                close()
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = EvenOdd) {
                moveTo(17.7878f, 17.4559f)
                curveTo(15.9809f, 17.039f, 14.0614f, 17.2426f, 12.373f, 18.0191f)
                curveTo(10.2556f, 18.9929f, 7.832f, 19.1884f, 5.5875f, 18.545f)
                lineTo(2.4929f, 17.6579f)
                curveTo(2.1318f, 17.5544f, 1.923f, 17.1777f, 2.0265f, 16.8166f)
                curveTo(2.13f, 16.4554f, 2.5067f, 16.2466f, 2.8678f, 16.3501f)
                lineTo(5.9624f, 17.2372f)
                curveTo(7.8899f, 17.7898f, 9.9789f, 17.6227f, 11.8046f, 16.7831f)
                curveTo(13.7629f, 15.8825f, 15.9896f, 15.6448f, 18.0937f, 16.1303f)
                lineTo(20.9726f, 16.7947f)
                curveTo(21.3386f, 16.8792f, 21.5669f, 17.2444f, 21.4824f, 17.6105f)
                curveTo(21.398f, 17.9765f, 21.0327f, 18.2048f, 20.6667f, 18.1203f)
                lineTo(17.7878f, 17.4559f)
                close()
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = EvenOdd) {
                moveTo(17.7878f, 6.5724f)
                curveTo(15.9809f, 6.1554f, 14.0614f, 6.3591f, 12.373f, 7.1356f)
                curveTo(10.2556f, 8.1094f, 7.832f, 8.3049f, 5.5875f, 7.6615f)
                lineTo(2.4929f, 6.7744f)
                curveTo(2.1318f, 6.6709f, 1.923f, 6.2942f, 2.0265f, 5.9331f)
                curveTo(2.13f, 5.5719f, 2.5067f, 5.3631f, 2.8678f, 5.4666f)
                lineTo(5.9624f, 6.3537f)
                curveTo(7.8899f, 6.9063f, 9.9789f, 6.7392f, 11.8046f, 5.8996f)
                curveTo(13.7629f, 4.999f, 15.9896f, 4.7613f, 18.0937f, 5.2468f)
                lineTo(20.9726f, 5.9112f)
                curveTo(21.3386f, 5.9956f, 21.5669f, 6.3609f, 21.4824f, 6.7269f)
                curveTo(21.398f, 7.093f, 21.0327f, 7.3213f, 20.6667f, 7.2368f)
                lineTo(17.7878f, 6.5724f)
                close()
            }
        }
        .build()
        return _hazzy!!
    }

private var _hazzy: ImageVector? = null
