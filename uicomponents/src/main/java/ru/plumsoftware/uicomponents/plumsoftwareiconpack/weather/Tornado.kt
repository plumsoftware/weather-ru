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

public val WeatherGroup.Tornado: ImageVector
    get() {
        if (_tornado != null) {
            return _tornado!!
        }
        _tornado = Builder(name = "Tornado", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
                viewportWidth = 24.0f, viewportHeight = 24.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = EvenOdd) {
                moveTo(6.3685f, 4.2067f)
                curveTo(7.7664f, 3.6371f, 9.6517f, 3.3f, 11.7f, 3.3f)
                curveTo(13.7483f, 3.3f, 15.6335f, 3.6371f, 17.0314f, 4.2067f)
                curveTo(17.7287f, 4.4908f, 18.337f, 4.846f, 18.7817f, 5.2743f)
                curveTo(19.2282f, 5.7045f, 19.55f, 6.2516f, 19.55f, 6.893f)
                curveTo(19.55f, 7.5345f, 19.2282f, 8.0815f, 18.7817f, 8.5117f)
                curveTo(18.337f, 8.9401f, 17.7287f, 9.2952f, 17.0314f, 9.5793f)
                curveTo(15.6335f, 10.1489f, 13.7483f, 10.486f, 11.7f, 10.486f)
                curveTo(9.6517f, 10.486f, 7.7664f, 10.1489f, 6.3685f, 9.5793f)
                curveTo(5.6713f, 9.2952f, 5.0629f, 8.9401f, 4.6183f, 8.5117f)
                curveTo(4.1718f, 8.0815f, 3.85f, 7.5345f, 3.85f, 6.893f)
                curveTo(3.85f, 6.2516f, 4.1718f, 5.7045f, 4.6183f, 5.2743f)
                curveTo(5.0629f, 4.846f, 5.6713f, 4.4908f, 6.3685f, 4.2067f)
                close()
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = EvenOdd) {
                moveTo(4.8155f, 10.9223f)
                curveTo(5.1013f, 10.6913f, 5.5201f, 10.7357f, 5.7511f, 11.0215f)
                curveTo(6.0728f, 11.4194f, 6.7811f, 11.8458f, 7.8627f, 12.1699f)
                curveTo(8.9201f, 12.4868f, 10.2477f, 12.6796f, 11.7f, 12.6796f)
                curveTo(13.1524f, 12.6796f, 14.48f, 12.4868f, 15.5373f, 12.1699f)
                curveTo(16.619f, 11.8458f, 17.3273f, 11.4194f, 17.6489f, 11.0215f)
                curveTo(17.8799f, 10.7357f, 18.2988f, 10.6913f, 18.5845f, 10.9223f)
                curveTo(18.8702f, 11.1533f, 18.9146f, 11.5722f, 18.6837f, 11.8579f)
                curveTo(18.1109f, 12.5665f, 17.0934f, 13.0926f, 15.9193f, 13.4444f)
                curveTo(14.7208f, 13.8036f, 13.2625f, 14.0101f, 11.7f, 14.0101f)
                curveTo(10.1376f, 14.0101f, 8.6793f, 13.8036f, 7.4808f, 13.4444f)
                curveTo(6.3066f, 13.0926f, 5.2892f, 12.5665f, 4.7164f, 11.8579f)
                curveTo(4.4854f, 11.5722f, 4.5298f, 11.1533f, 4.8155f, 10.9223f)
                close()
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = EvenOdd) {
                moveTo(7.578f, 15.1915f)
                curveTo(7.8005f, 14.8991f, 8.2179f, 14.8424f, 8.5103f, 15.0649f)
                curveTo(9.0275f, 15.4585f, 10.2056f, 15.8063f, 11.6998f, 15.8063f)
                curveTo(13.194f, 15.8063f, 14.3721f, 15.4585f, 14.8893f, 15.0649f)
                curveTo(15.1817f, 14.8424f, 15.5991f, 14.8991f, 15.8216f, 15.1915f)
                curveTo(16.0441f, 15.4838f, 15.9874f, 15.9012f, 15.695f, 16.1237f)
                curveTo(14.8402f, 16.7743f, 13.3155f, 17.1368f, 11.6998f, 17.1368f)
                curveTo(10.0841f, 17.1368f, 8.5595f, 16.7743f, 7.7046f, 16.1237f)
                curveTo(7.4122f, 15.9012f, 7.3555f, 15.4838f, 7.578f, 15.1915f)
                close()
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = EvenOdd) {
                moveTo(10.3845f, 19.2795f)
                curveTo(10.555f, 18.9528f, 10.9568f, 18.825f, 11.2818f, 18.994f)
                curveTo(11.3993f, 19.0552f, 11.7838f, 19.1248f, 12.3371f, 19.0282f)
                curveTo(12.8905f, 18.9317f, 13.2303f, 18.7358f, 13.3212f, 18.6383f)
                curveTo(13.5725f, 18.3688f, 13.9931f, 18.3531f, 14.2606f, 18.6034f)
                curveTo(14.5281f, 18.8536f, 14.5413f, 19.275f, 14.29f, 19.5445f)
                curveTo(13.9091f, 19.953f, 13.2305f, 20.223f, 12.5577f, 20.3403f)
                curveTo(11.8849f, 20.4577f, 11.1567f, 20.4331f, 10.6641f, 20.177f)
                curveTo(10.3391f, 20.008f, 10.2139f, 19.6062f, 10.3845f, 19.2795f)
                close()
            }
        }
        .build()
        return _tornado!!
    }

private var _tornado: ImageVector? = null
