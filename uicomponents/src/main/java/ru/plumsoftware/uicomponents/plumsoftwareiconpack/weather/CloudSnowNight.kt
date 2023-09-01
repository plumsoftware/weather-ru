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

public val WeatherGroup.CloudSnowNight: ImageVector
    get() {
        if (_cloudSnowNight != null) {
            return _cloudSnowNight!!
        }
        _cloudSnowNight = Builder(name = "CloudSnowNight", defaultWidth = 24.0.dp, defaultHeight =
                24.0.dp, viewportWidth = 24.0f, viewportHeight = 24.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = EvenOdd) {
                moveTo(15.0846f, 18.414f)
                lineTo(16.9737f, 19.5723f)
                lineTo(18.0103f, 19.3831f)
                curveTo(18.282f, 19.3335f, 18.5424f, 19.5136f, 18.592f, 19.7852f)
                curveTo(18.638f, 20.0374f, 18.4862f, 20.2798f, 18.2469f, 20.353f)
                curveTo(18.4767f, 20.4991f, 18.5477f, 20.8032f, 18.4048f, 21.0362f)
                curveTo(18.2649f, 21.2643f, 17.9715f, 21.3407f, 17.7392f, 21.2139f)
                lineTo(17.7434f, 21.226f)
                curveTo(17.8343f, 21.4868f, 17.6965f, 21.7719f, 17.4358f, 21.8627f)
                curveTo(17.175f, 21.9536f, 16.89f, 21.8158f, 16.7991f, 21.555f)
                lineTo(16.3929f, 20.3892f)
                lineTo(15.5838f, 19.8931f)
                lineTo(15.5838f, 20.9264f)
                lineTo(16.3051f, 21.8402f)
                curveTo(16.4762f, 22.057f, 16.4392f, 22.3714f, 16.2224f, 22.5425f)
                curveTo(16.0301f, 22.6943f, 15.7609f, 22.6822f, 15.5832f, 22.5262f)
                curveTo(15.5695f, 22.7902f, 15.3512f, 23.0f, 15.0838f, 23.0f)
                curveTo(14.8165f, 23.0f, 14.5981f, 22.7902f, 14.5845f, 22.5262f)
                curveTo(14.4067f, 22.6822f, 14.1375f, 22.6943f, 13.9452f, 22.5425f)
                curveTo(13.7285f, 22.3714f, 13.6915f, 22.057f, 13.8626f, 21.8402f)
                lineTo(14.5838f, 20.9264f)
                lineTo(14.5838f, 19.8903f)
                lineTo(13.7829f, 20.3785f)
                lineTo(13.4011f, 21.4745f)
                curveTo(13.3102f, 21.7353f, 13.0252f, 21.873f, 12.7644f, 21.7821f)
                curveTo(12.5198f, 21.6969f, 12.3834f, 21.4408f, 12.4425f, 21.1941f)
                curveTo(12.2113f, 21.314f, 11.9234f, 21.236f, 11.7858f, 21.0102f)
                curveTo(11.6421f, 20.7744f, 11.7167f, 20.4668f, 11.9525f, 20.3231f)
                lineTo(12.0122f, 20.2867f)
                lineTo(12.0103f, 20.2863f)
                curveTo(11.7387f, 20.2367f, 11.5586f, 19.9763f, 11.6082f, 19.7047f)
                curveTo(11.6578f, 19.433f, 11.9182f, 19.253f, 12.1898f, 19.3026f)
                lineTo(13.2957f, 19.5043f)
                lineTo(15.0846f, 18.414f)
                close()
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = EvenOdd) {
                moveTo(20.2416f, 7.896f)
                curveTo(20.9848f, 8.9269f, 21.3084f, 10.2191f, 20.9757f, 11.6411f)
                curveTo(22.6118f, 12.0326f, 23.5705f, 13.4661f, 23.5993f, 14.915f)
                curveTo(23.6157f, 15.7372f, 23.3349f, 16.5778f, 22.7099f, 17.2344f)
                curveTo(22.0826f, 17.8934f, 21.162f, 18.3125f, 19.9918f, 18.3979f)
                lineTo(19.9634f, 18.4f)
                lineTo(18.5296f, 18.3974f)
                curveTo(18.551f, 18.3552f, 18.5668f, 18.3092f, 18.5757f, 18.2603f)
                curveTo(18.6235f, 17.9983f, 18.4578f, 17.7468f, 18.2024f, 17.6848f)
                lineTo(18.2152f, 17.677f)
                curveTo(18.451f, 17.5332f, 18.5256f, 17.2256f, 18.3819f, 16.9898f)
                curveTo(18.2458f, 16.7665f, 17.9628f, 16.6877f, 17.733f, 16.802f)
                curveTo(17.8118f, 16.5461f, 17.6745f, 16.2717f, 17.4195f, 16.1828f)
                curveTo(17.1588f, 16.092f, 16.8737f, 16.2297f, 16.7829f, 16.4905f)
                lineTo(16.3899f, 17.6184f)
                lineTo(15.5839f, 18.1097f)
                verticalLineTo(17.0736f)
                lineTo(16.3051f, 16.1598f)
                curveTo(16.4762f, 15.943f, 16.4392f, 15.6286f, 16.2225f, 15.4575f)
                curveTo(16.0301f, 15.3057f, 15.7609f, 15.3178f, 15.5832f, 15.4738f)
                curveTo(15.5696f, 15.2098f, 15.3512f, 15.0f, 15.0839f, 15.0f)
                curveTo(14.8165f, 15.0f, 14.5982f, 15.2098f, 14.5845f, 15.4738f)
                curveTo(14.4068f, 15.3178f, 14.1376f, 15.3057f, 13.9452f, 15.4575f)
                curveTo(13.7285f, 15.6286f, 13.6915f, 15.943f, 13.8626f, 16.1598f)
                lineTo(14.5839f, 17.0736f)
                verticalLineTo(18.1202f)
                lineTo(13.8031f, 17.6443f)
                lineTo(13.4011f, 16.4905f)
                curveTo(13.3102f, 16.2297f, 13.0252f, 16.092f, 12.7644f, 16.1828f)
                curveTo(12.5037f, 16.2737f, 12.3659f, 16.5587f, 12.4568f, 16.8195f)
                lineTo(12.4595f, 16.8274f)
                curveTo(12.2295f, 16.7123f, 11.9457f, 16.791f, 11.8094f, 17.0146f)
                curveTo(11.6692f, 17.2446f, 11.7367f, 17.5429f, 11.9589f, 17.6909f)
                curveTo(11.7164f, 17.7619f, 11.5619f, 18.0062f, 11.6082f, 18.2603f)
                curveTo(11.6163f, 18.3045f, 11.63f, 18.3463f, 11.6483f, 18.3851f)
                lineTo(10.6171f, 18.3832f)
                lineTo(10.5959f, 18.382f)
                curveTo(7.4939f, 18.204f, 5.7214f, 16.3031f, 5.6059f, 14.1191f)
                curveTo(5.4972f, 12.0622f, 6.8872f, 10.0453f, 9.4331f, 9.4423f)
                curveTo(9.962f, 7.7463f, 11.0278f, 6.6084f, 12.3294f, 5.9834f)
                curveTo(13.6934f, 5.3284f, 15.2667f, 5.2581f, 16.6783f, 5.6076f)
                curveTo(18.0866f, 5.9562f, 19.4097f, 6.742f, 20.2416f, 7.896f)
                close()
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = EvenOdd) {
                moveTo(6.7315f, 0.9806f)
                curveTo(6.2719f, 1.4163f, 6.5169f, 2.2502f, 6.6948f, 2.5751f)
                lineTo(6.6954f, 2.5761f)
                curveTo(6.9234f, 2.9907f, 7.0608f, 3.4503f, 7.0828f, 3.9434f)
                lineTo(7.0829f, 3.9457f)
                curveTo(7.1715f, 5.7721f, 5.5726f, 7.4353f, 3.5077f, 7.6219f)
                curveTo(3.2017f, 7.6497f, 2.9079f, 7.646f, 2.6218f, 7.615f)
                lineTo(2.6241f, 7.6153f)
                curveTo(2.3912f, 7.5894f, 2.1439f, 7.5919f, 1.9105f, 7.6544f)
                curveTo(1.6713f, 7.7183f, 1.4087f, 7.8583f, 1.2511f, 8.1209f)
                curveTo(1.0942f, 8.3825f, 1.1073f, 8.6564f, 1.1757f, 8.8702f)
                curveTo(1.2426f, 9.0793f, 1.371f, 9.2674f, 1.5181f, 9.4297f)
                curveTo(2.1936f, 10.1815f, 3.2972f, 11.0984f, 4.3283f, 11.4243f)
                curveTo(4.6037f, 11.5114f, 4.9113f, 11.4371f, 5.1058f, 11.2365f)
                lineTo(5.2646f, 11.0726f)
                curveTo(5.9349f, 10.381f, 6.6156f, 9.5462f, 7.594f, 9.3141f)
                lineTo(8.2428f, 9.1602f)
                curveTo(8.4779f, 9.1044f, 8.6673f, 8.9447f, 8.7464f, 8.7356f)
                lineTo(8.9822f, 8.1119f)
                curveTo(9.3258f, 7.2029f, 9.9741f, 6.4124f, 10.8352f, 5.8523f)
                lineTo(11.6363f, 5.3312f)
                curveTo(11.8823f, 5.1712f, 11.9939f, 4.8894f, 11.9155f, 4.6257f)
                curveTo(11.4146f, 2.9399f, 9.9507f, 1.3602f, 8.1671f, 0.785f)
                curveTo(7.7952f, 0.6646f, 7.168f, 0.5667f, 6.7315f, 0.9806f)
                close()
            }
        }
        .build()
        return _cloudSnowNight!!
    }

private var _cloudSnowNight: ImageVector? = null
