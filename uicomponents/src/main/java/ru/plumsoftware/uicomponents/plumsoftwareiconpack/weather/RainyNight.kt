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
import androidx.compose.ui.graphics.vector.group
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import ru.plumsoftware.uicomponents.plumsoftwareiconpack.WeatherGroup

public val WeatherGroup.RainyNight: ImageVector
    get() {
        if (_rainyNight != null) {
            return _rainyNight!!
        }
        _rainyNight = Builder(name = "RainyNight", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
                viewportWidth = 24.0f, viewportHeight = 24.0f).apply {
            group {
                path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                        strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                        pathFillType = EvenOdd) {
                    moveTo(20.7023f, 12.0811f)
                    curveTo(21.0347f, 10.6389f, 20.7108f, 9.3284f, 19.9675f, 8.2829f)
                    curveTo(19.1354f, 7.1126f, 17.8123f, 6.3158f, 16.4043f, 5.9623f)
                    curveTo(14.993f, 5.608f, 13.42f, 5.6793f, 12.0563f, 6.3435f)
                    curveTo(10.7549f, 6.9773f, 9.6889f, 8.1311f, 9.1599f, 9.8513f)
                    curveTo(6.4749f, 10.4963f, 5.0769f, 12.738f, 5.363f, 14.9286f)
                    curveTo(5.5104f, 16.0581f, 6.1064f, 17.1238f, 7.1341f, 17.8709f)
                    curveTo(8.1589f, 18.6158f, 9.5687f, 19.0146f, 11.3185f, 18.9194f)
                    curveTo(12.3038f, 18.8658f, 14.4558f, 18.8932f, 16.37f, 18.9177f)
                    curveTo(17.3247f, 18.9298f, 18.2202f, 18.9413f, 18.8824f, 18.9415f)
                    curveTo(20.2286f, 19.0148f, 21.3087f, 18.6983f, 22.0819f, 18.0818f)
                    curveTo(22.8626f, 17.4593f, 23.2625f, 16.5829f, 23.3192f, 15.6943f)
                    curveTo(23.4193f, 14.1285f, 22.4472f, 12.5044f, 20.7023f, 12.0811f)
                    close()
                }
                path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                        strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                        pathFillType = EvenOdd) {
                    moveTo(6.6383f, 1.0915f)
                    curveTo(6.1727f, 1.4949f, 6.3744f, 2.3159f, 6.5332f, 2.6394f)
                    lineTo(6.5337f, 2.6404f)
                    curveTo(6.7373f, 3.0532f, 6.8507f, 3.5058f, 6.8506f, 3.9859f)
                    lineTo(6.8506f, 3.9881f)
                    curveTo(6.8572f, 5.7668f, 5.231f, 7.3134f, 3.2163f, 7.4048f)
                    curveTo(2.9177f, 7.4185f, 2.6324f, 7.4021f, 2.3557f, 7.3595f)
                    lineTo(2.3579f, 7.3599f)
                    curveTo(2.1328f, 7.3246f, 1.8924f, 7.3163f, 1.6628f, 7.3668f)
                    curveTo(1.4276f, 7.4185f, 1.1663f, 7.5431f, 1.0017f, 7.7914f)
                    curveTo(0.8379f, 8.0388f, 0.8387f, 8.3055f, 0.8958f, 8.5162f)
                    curveTo(0.9517f, 8.7223f, 1.0683f, 8.9108f, 1.2041f, 9.0749f)
                    curveTo(1.8278f, 9.8349f, 2.8603f, 10.7739f, 3.8481f, 11.1356f)
                    curveTo(4.1119f, 11.2322f, 4.4141f, 11.1733f, 4.6118f, 10.9869f)
                    lineTo(4.7733f, 10.8345f)
                    curveTo(5.4548f, 10.1917f, 6.1526f, 9.4101f, 7.1135f, 9.2271f)
                    lineTo(7.7506f, 9.1059f)
                    curveTo(7.9815f, 9.0619f, 8.1725f, 8.915f, 8.2585f, 8.7152f)
                    lineTo(8.5148f, 8.1194f)
                    curveTo(8.8883f, 7.251f, 9.5527f, 6.5111f, 10.4139f, 6.0043f)
                    lineTo(11.215f, 5.5329f)
                    curveTo(11.4611f, 5.3881f, 11.5818f, 5.1191f, 11.5171f, 4.8595f)
                    curveTo(11.1038f, 3.1995f, 9.75f, 1.6006f, 8.0419f, 0.964f)
                    curveTo(7.6858f, 0.8308f, 7.0805f, 0.7084f, 6.6383f, 1.0915f)
                    close()
                }
                path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                        strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                        pathFillType = EvenOdd) {
                    moveTo(11.3885f, 19.5568f)
                    curveTo(11.748f, 19.7624f, 11.8728f, 20.2206f, 11.6671f, 20.5802f)
                    lineTo(10.5568f, 22.5211f)
                    curveTo(10.3512f, 22.8807f, 9.893f, 23.0054f, 9.5334f, 22.7998f)
                    curveTo(9.1739f, 22.5941f, 9.0491f, 22.1359f, 9.2548f, 21.7763f)
                    lineTo(10.3651f, 19.8354f)
                    curveTo(10.5707f, 19.4758f, 11.0289f, 19.3511f, 11.3885f, 19.5568f)
                    close()
                }
                path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                        strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                        pathFillType = EvenOdd) {
                    moveTo(16.9885f, 19.6569f)
                    curveTo(17.348f, 19.8625f, 17.4728f, 20.3207f, 17.2671f, 20.6803f)
                    lineTo(16.1568f, 22.6212f)
                    curveTo(15.9512f, 22.9808f, 15.493f, 23.1055f, 15.1334f, 22.8999f)
                    curveTo(14.7739f, 22.6942f, 14.6491f, 22.236f, 14.8548f, 21.8764f)
                    lineTo(15.9651f, 19.9355f)
                    curveTo(16.1707f, 19.5759f, 16.6289f, 19.4512f, 16.9885f, 19.6569f)
                    close()
                }
                path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                        strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                        pathFillType = EvenOdd) {
                    moveTo(13.6073f, 20.8099f)
                    curveTo(13.9687f, 21.0123f, 14.0976f, 21.4693f, 13.8952f, 21.8307f)
                    lineTo(13.3489f, 22.8062f)
                    curveTo(13.1465f, 23.1676f, 12.6894f, 23.2965f, 12.328f, 23.0941f)
                    curveTo(11.9666f, 22.8917f, 11.8377f, 22.4346f, 12.0401f, 22.0732f)
                    lineTo(12.5864f, 21.0978f)
                    curveTo(12.7888f, 20.7364f, 13.2459f, 20.6075f, 13.6073f, 20.8099f)
                    close()
                }
                path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                        strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                        pathFillType = EvenOdd) {
                    moveTo(19.4836f, 20.921f)
                    curveTo(19.845f, 21.1234f, 19.9739f, 21.5804f, 19.7715f, 21.9418f)
                    lineTo(19.2252f, 22.9173f)
                    curveTo(19.0228f, 23.2787f, 18.5657f, 23.4076f, 18.2043f, 23.2052f)
                    curveTo(17.8429f, 23.0028f, 17.714f, 22.5457f, 17.9164f, 22.1843f)
                    lineTo(18.4627f, 21.2088f)
                    curveTo(18.6651f, 20.8474f, 19.1222f, 20.7186f, 19.4836f, 20.921f)
                    close()
                }
            }
        }
        .build()
        return _rainyNight!!
    }

private var _rainyNight: ImageVector? = null
