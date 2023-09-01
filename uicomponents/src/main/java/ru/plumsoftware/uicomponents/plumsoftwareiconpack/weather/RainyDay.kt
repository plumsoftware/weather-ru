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

public val WeatherGroup.RainyDay: ImageVector
    get() {
        if (_rainyDay != null) {
            return _rainyDay!!
        }
        _rainyDay = Builder(name = "RainyDay", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
                viewportWidth = 24.0f, viewportHeight = 24.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = EvenOdd) {
                moveTo(4.968f, 1.241f)
                curveTo(5.3059f, 1.1135f, 5.6797f, 1.2933f, 5.8031f, 1.6426f)
                lineTo(6.1403f, 2.5973f)
                curveTo(6.2636f, 2.9466f, 6.0897f, 3.3332f, 5.7519f, 3.4607f)
                curveTo(5.414f, 3.5882f, 5.0401f, 3.4085f, 4.9167f, 3.0592f)
                lineTo(4.5796f, 2.1044f)
                curveTo(4.4562f, 1.7551f, 4.6301f, 1.3686f, 4.968f, 1.241f)
                close()
                moveTo(9.7457f, 1.4948f)
                curveTo(10.0727f, 1.6497f, 10.2163f, 2.0494f, 10.0664f, 2.3874f)
                lineTo(9.6796f, 3.2602f)
                curveTo(9.5297f, 3.5983f, 9.1432f, 3.7467f, 8.8162f, 3.5918f)
                curveTo(8.4892f, 3.4369f, 8.3456f, 3.0372f, 8.4955f, 2.6992f)
                lineTo(8.8823f, 1.8264f)
                curveTo(9.0322f, 1.4883f, 9.4187f, 1.3398f, 9.7457f, 1.4948f)
                close()
                moveTo(5.9373f, 3.9962f)
                curveTo(7.2748f, 3.4832f, 8.7183f, 3.8831f, 9.6418f, 4.8865f)
                lineTo(10.2f, 5.493f)
                lineTo(9.5074f, 5.9266f)
                curveTo(8.7098f, 6.426f, 8.1187f, 7.2373f, 7.8658f, 8.1706f)
                lineTo(7.7694f, 8.5265f)
                lineTo(7.3343f, 8.6625f)
                curveTo(6.6412f, 8.8792f, 5.997f, 9.2371f, 5.4398f, 9.7151f)
                lineTo(4.8745f, 10.2f)
                lineTo(4.4592f, 9.6752f)
                curveTo(4.2171f, 9.3691f, 4.0202f, 9.0168f, 3.8822f, 8.626f)
                curveTo(3.2234f, 6.7606f, 4.1385f, 4.6862f, 5.9373f, 3.9962f)
                close()
                moveTo(1.485f, 4.6606f)
                curveTo(1.6368f, 4.3235f, 2.0242f, 4.1775f, 2.3503f, 4.3345f)
                lineTo(3.1878f, 4.7377f)
                curveTo(3.5138f, 4.8946f, 3.6551f, 5.2952f, 3.5033f, 5.6323f)
                curveTo(3.3514f, 5.9694f, 2.964f, 6.1154f, 2.638f, 5.9585f)
                lineTo(1.8005f, 5.5553f)
                curveTo(1.4744f, 5.3983f, 1.3332f, 4.9978f, 1.485f, 4.6606f)
                close()
                moveTo(3.3429f, 8.8365f)
                curveTo(3.468f, 9.1851f, 3.296f, 9.5726f, 2.9588f, 9.702f)
                lineTo(2.078f, 10.0398f)
                curveTo(1.7407f, 10.1692f, 1.366f, 9.9914f, 1.2408f, 9.6427f)
                curveTo(1.1157f, 9.2941f, 1.2877f, 8.9066f, 1.6249f, 8.7773f)
                lineTo(2.5058f, 8.4394f)
                curveTo(2.843f, 8.31f, 3.2178f, 8.4878f, 3.3429f, 8.8365f)
                close()
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = EvenOdd) {
                moveTo(19.9847f, 11.6292f)
                curveTo(20.3171f, 10.187f, 19.9932f, 8.8764f, 19.2498f, 7.831f)
                curveTo(18.4178f, 6.6607f, 17.0947f, 5.8639f, 15.6867f, 5.5104f)
                curveTo(14.2754f, 5.1561f, 12.7024f, 5.2274f, 11.3386f, 5.8916f)
                curveTo(10.0373f, 6.5253f, 8.9713f, 7.6792f, 8.4422f, 9.3994f)
                curveTo(5.7573f, 10.0444f, 4.3593f, 12.2861f, 4.6453f, 14.4767f)
                curveTo(4.7928f, 15.6061f, 5.3888f, 16.6719f, 6.4165f, 17.419f)
                curveTo(7.4413f, 18.1639f, 8.8511f, 18.5626f, 10.6008f, 18.4675f)
                curveTo(11.5861f, 18.4139f, 13.7381f, 18.4413f, 15.6524f, 18.4658f)
                curveTo(16.6071f, 18.4779f, 17.5026f, 18.4894f, 18.1648f, 18.4896f)
                curveTo(19.511f, 18.5629f, 20.591f, 18.2464f, 21.3642f, 17.6298f)
                curveTo(22.1449f, 17.0074f, 22.5448f, 16.131f, 22.6016f, 15.2424f)
                curveTo(22.7017f, 13.6766f, 21.7296f, 12.0525f, 19.9847f, 11.6292f)
                close()
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = EvenOdd) {
                moveTo(10.6708f, 19.1049f)
                curveTo(11.0304f, 19.3105f, 11.1551f, 19.7687f, 10.9495f, 20.1283f)
                lineTo(9.8392f, 22.0692f)
                curveTo(9.6335f, 22.4288f, 9.1753f, 22.5535f, 8.8158f, 22.3479f)
                curveTo(8.4562f, 22.1422f, 8.3315f, 21.684f, 8.5372f, 21.3244f)
                lineTo(9.6474f, 19.3835f)
                curveTo(9.8531f, 19.0239f, 10.3113f, 18.8992f, 10.6708f, 19.1049f)
                close()
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = EvenOdd) {
                moveTo(16.2709f, 19.205f)
                curveTo(16.6304f, 19.4106f, 16.7552f, 19.8688f, 16.5495f, 20.2284f)
                lineTo(15.4392f, 22.1693f)
                curveTo(15.2335f, 22.5289f, 14.7753f, 22.6536f, 14.4158f, 22.4479f)
                curveTo(14.0563f, 22.2423f, 13.9315f, 21.7841f, 14.1372f, 21.4245f)
                lineTo(15.2475f, 19.4836f)
                curveTo(15.4531f, 19.124f, 15.9113f, 18.9993f, 16.2709f, 19.205f)
                close()
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = EvenOdd) {
                moveTo(12.8896f, 20.358f)
                curveTo(13.251f, 20.5604f, 13.3799f, 21.0174f, 13.1775f, 21.3788f)
                lineTo(12.6312f, 22.3543f)
                curveTo(12.4288f, 22.7157f, 11.9718f, 22.8446f, 11.6104f, 22.6422f)
                curveTo(11.249f, 22.4398f, 11.1201f, 21.9827f, 11.3225f, 21.6213f)
                lineTo(11.8688f, 20.6459f)
                curveTo(12.0712f, 20.2845f, 12.5282f, 20.1556f, 12.8896f, 20.358f)
                close()
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = EvenOdd) {
                moveTo(18.766f, 20.469f)
                curveTo(19.1274f, 20.6714f, 19.2563f, 21.1285f, 19.0539f, 21.4899f)
                lineTo(18.5075f, 22.4654f)
                curveTo(18.3051f, 22.8268f, 17.8481f, 22.9557f, 17.4867f, 22.7533f)
                curveTo(17.1253f, 22.5509f, 16.9964f, 22.0938f, 17.1988f, 21.7324f)
                lineTo(17.7451f, 20.7569f)
                curveTo(17.9475f, 20.3955f, 18.4046f, 20.2666f, 18.766f, 20.469f)
                close()
            }
        }
        .build()
        return _rainyDay!!
    }

private var _rainyDay: ImageVector? = null
