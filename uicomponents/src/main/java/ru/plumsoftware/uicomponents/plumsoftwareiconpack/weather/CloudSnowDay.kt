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

public val WeatherGroup.CloudSnowDay: ImageVector
    get() {
        if (_cloudSnowDay != null) {
            return _cloudSnowDay!!
        }
        _cloudSnowDay = Builder(name = "CloudSnowDay", defaultWidth = 24.0.dp, defaultHeight =
                24.0.dp, viewportWidth = 24.0f, viewportHeight = 24.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = EvenOdd) {
                moveTo(15.0846f, 18.7f)
                lineTo(16.9737f, 19.8583f)
                lineTo(18.0103f, 19.6691f)
                curveTo(18.282f, 19.6196f, 18.5424f, 19.7996f, 18.5919f, 20.0713f)
                curveTo(18.6379f, 20.3234f, 18.4861f, 20.5659f, 18.2469f, 20.639f)
                curveTo(18.4767f, 20.7852f, 18.5476f, 21.0892f, 18.4047f, 21.3223f)
                curveTo(18.2649f, 21.5504f, 17.9714f, 21.6268f, 17.7392f, 21.4999f)
                lineTo(17.7434f, 21.5121f)
                curveTo(17.8343f, 21.7728f, 17.6965f, 22.0579f, 17.4358f, 22.1487f)
                curveTo(17.175f, 22.2396f, 16.8899f, 22.1018f, 16.7991f, 21.8411f)
                lineTo(16.3929f, 20.6752f)
                lineTo(15.5838f, 20.1791f)
                lineTo(15.5838f, 21.2125f)
                lineTo(16.3051f, 22.1262f)
                curveTo(16.4762f, 22.343f, 16.4392f, 22.6574f, 16.2224f, 22.8285f)
                curveTo(16.0301f, 22.9803f, 15.7609f, 22.9683f, 15.5831f, 22.8122f)
                curveTo(15.5695f, 23.0762f, 15.3512f, 23.286f, 15.0838f, 23.286f)
                curveTo(14.8164f, 23.286f, 14.5981f, 23.0762f, 14.5845f, 22.8122f)
                curveTo(14.4067f, 22.9683f, 14.1375f, 22.9803f, 13.9452f, 22.8285f)
                curveTo(13.7284f, 22.6574f, 13.6914f, 22.343f, 13.8625f, 22.1262f)
                lineTo(14.5838f, 21.2125f)
                lineTo(14.5838f, 20.1763f)
                lineTo(13.7829f, 20.6645f)
                lineTo(13.401f, 21.7605f)
                curveTo(13.3102f, 22.0213f, 13.0251f, 22.159f, 12.7644f, 22.0682f)
                curveTo(12.5197f, 21.9829f, 12.3834f, 21.7268f, 12.4425f, 21.4801f)
                curveTo(12.2112f, 21.6f, 11.9234f, 21.522f, 11.7858f, 21.2963f)
                curveTo(11.642f, 21.0605f, 11.7167f, 20.7528f, 11.9525f, 20.6091f)
                lineTo(12.0122f, 20.5727f)
                lineTo(12.0103f, 20.5723f)
                curveTo(11.7387f, 20.5228f, 11.5586f, 20.2624f, 11.6082f, 19.9907f)
                curveTo(11.6578f, 19.7191f, 11.9182f, 19.539f, 12.1898f, 19.5886f)
                lineTo(13.2957f, 19.7904f)
                lineTo(15.0846f, 18.7f)
                close()
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = EvenOdd) {
                moveTo(19.4849f, 8.15f)
                curveTo(20.2075f, 9.1413f, 20.5222f, 10.3838f, 20.1986f, 11.751f)
                curveTo(21.7893f, 12.1275f, 22.7213f, 13.5059f, 22.7494f, 14.899f)
                curveTo(22.7653f, 15.6896f, 22.4923f, 16.4978f, 21.8846f, 17.1292f)
                curveTo(21.2748f, 17.7629f, 20.3798f, 18.1659f, 19.2421f, 18.248f)
                lineTo(19.2144f, 18.25f)
                lineTo(18.532f, 18.2488f)
                curveTo(18.4683f, 18.1204f, 18.3504f, 18.0208f, 18.2023f, 17.9848f)
                lineTo(18.2152f, 17.977f)
                curveTo(18.451f, 17.8332f, 18.5256f, 17.5256f, 18.3819f, 17.2898f)
                curveTo(18.2458f, 17.0665f, 17.9628f, 16.9877f, 17.7329f, 17.102f)
                curveTo(17.8118f, 16.8461f, 17.6744f, 16.5717f, 17.4195f, 16.4828f)
                curveTo(17.1587f, 16.392f, 16.8737f, 16.5297f, 16.7828f, 16.7905f)
                lineTo(16.3899f, 17.9184f)
                lineTo(15.8556f, 18.244f)
                lineTo(15.5838f, 18.2435f)
                verticalLineTo(17.3736f)
                lineTo(16.3051f, 16.4598f)
                curveTo(16.4762f, 16.243f, 16.4392f, 15.9286f, 16.2224f, 15.7575f)
                curveTo(16.0301f, 15.6057f, 15.7609f, 15.6178f, 15.5832f, 15.7738f)
                curveTo(15.5695f, 15.5098f, 15.3512f, 15.3f, 15.0838f, 15.3f)
                curveTo(14.8165f, 15.3f, 14.5981f, 15.5098f, 14.5845f, 15.7738f)
                curveTo(14.4067f, 15.6178f, 14.1375f, 15.6057f, 13.9452f, 15.7575f)
                curveTo(13.7285f, 15.9286f, 13.6915f, 16.243f, 13.8625f, 16.4598f)
                lineTo(14.5838f, 17.3736f)
                verticalLineTo(18.2418f)
                lineTo(14.2902f, 18.2412f)
                lineTo(13.8031f, 17.9443f)
                lineTo(13.4011f, 16.7905f)
                curveTo(13.3102f, 16.5297f, 13.0252f, 16.392f, 12.7644f, 16.4828f)
                curveTo(12.5036f, 16.5737f, 12.3659f, 16.8587f, 12.4567f, 17.1195f)
                lineTo(12.4595f, 17.1274f)
                curveTo(12.2294f, 17.0123f, 11.9457f, 17.091f, 11.8093f, 17.3146f)
                curveTo(11.6692f, 17.5446f, 11.7367f, 17.8429f, 11.9589f, 17.9909f)
                curveTo(11.8261f, 18.0298f, 11.7197f, 18.1206f, 11.6582f, 18.2366f)
                lineTo(10.1277f, 18.2339f)
                lineTo(10.1072f, 18.2327f)
                curveTo(7.0913f, 18.0615f, 5.368f, 16.2338f, 5.2558f, 14.1337f)
                curveTo(5.1501f, 12.156f, 6.5015f, 10.2166f, 8.9766f, 9.6369f)
                curveTo(9.4908f, 8.0061f, 10.527f, 6.912f, 11.7925f, 6.3109f)
                curveTo(13.1186f, 5.6812f, 14.6482f, 5.6136f, 16.0206f, 5.9496f)
                curveTo(17.3898f, 6.2849f, 18.6761f, 7.0404f, 19.4849f, 8.15f)
                close()
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = EvenOdd) {
                moveTo(5.0388f, 1.2237f)
                curveTo(5.4237f, 1.0707f, 5.8598f, 1.2588f, 6.0127f, 1.6437f)
                lineTo(6.4307f, 2.6958f)
                curveTo(6.5837f, 3.0807f, 6.3956f, 3.5168f, 6.0107f, 3.6697f)
                curveTo(5.6257f, 3.8227f, 5.1897f, 3.6346f, 5.0367f, 3.2497f)
                lineTo(4.6187f, 2.1976f)
                curveTo(4.4658f, 1.8127f, 4.6538f, 1.3766f, 5.0388f, 1.2237f)
                close()
                moveTo(10.5467f, 1.3515f)
                curveTo(10.9279f, 1.5134f, 11.1057f, 1.9538f, 10.9438f, 2.335f)
                lineTo(10.5258f, 3.3193f)
                curveTo(10.3639f, 3.7006f, 9.9236f, 3.8784f, 9.5423f, 3.7165f)
                curveTo(9.1611f, 3.5546f, 8.9832f, 3.1143f, 9.1452f, 2.733f)
                lineTo(9.5632f, 1.7487f)
                curveTo(9.7251f, 1.3674f, 10.1654f, 1.1896f, 10.5467f, 1.3515f)
                close()
                moveTo(6.241f, 4.26f)
                curveTo(7.7646f, 3.6455f, 9.4388f, 4.044f, 10.5333f, 5.1313f)
                lineTo(11.1948f, 5.7885f)
                lineTo(10.4111f, 6.2937f)
                curveTo(9.5086f, 6.8755f, 8.8535f, 7.798f, 8.5917f, 8.8454f)
                lineTo(8.4918f, 9.2448f)
                lineTo(7.9952f, 9.4103f)
                curveTo(7.2041f, 9.674f, 6.4738f, 10.0933f, 5.8473f, 10.6436f)
                lineTo(5.2117f, 11.2018f)
                lineTo(4.7173f, 10.6309f)
                curveTo(4.4289f, 10.298f, 4.1912f, 9.9121f, 4.0201f, 9.4814f)
                curveTo(3.2033f, 7.4257f, 4.1918f, 5.0865f, 6.241f, 4.26f)
                close()
                moveTo(1.1364f, 5.1439f)
                curveTo(1.3006f, 4.7637f, 1.742f, 4.5885f, 2.1223f, 4.7527f)
                lineTo(3.099f, 5.1745f)
                curveTo(3.4792f, 5.3388f, 3.6544f, 5.7802f, 3.4902f, 6.1604f)
                curveTo(3.3259f, 6.5407f, 2.8845f, 6.7158f, 2.5043f, 6.5516f)
                lineTo(1.5276f, 6.1298f)
                curveTo(1.1473f, 5.9656f, 0.9722f, 5.5242f, 1.1364f, 5.1439f)
                close()
                moveTo(3.4059f, 9.7332f)
                curveTo(3.5608f, 10.1174f, 3.375f, 10.5544f, 2.9909f, 10.7093f)
                lineTo(1.9875f, 11.114f)
                curveTo(1.6033f, 11.269f, 1.1663f, 11.0831f, 1.0114f, 10.699f)
                curveTo(0.8564f, 10.3149f, 1.0422f, 9.8778f, 1.4264f, 9.7229f)
                lineTo(2.4298f, 9.3182f)
                curveTo(2.8139f, 9.1633f, 3.251f, 9.3491f, 3.4059f, 9.7332f)
                close()
            }
        }
        .build()
        return _cloudSnowDay!!
    }

private var _cloudSnowDay: ImageVector? = null
