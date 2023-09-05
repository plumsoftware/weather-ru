package ru.plumsoftware.uicomponents.plumsoftwareiconpack.weather

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.PathFillType.Companion.EvenOdd
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
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

public val WeatherGroup.Drops: ImageVector
    get() {
        if (_drops != null) {
            return _drops!!
        }
        _drops = Builder(name = "Drops", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
                viewportWidth = 24.0f, viewportHeight = 24.0f).apply {
            group {
                path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                        strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                        pathFillType = NonZero) {
                    moveTo(13.7818f, 11.6726f)
                    curveTo(13.7167f, 11.4932f, 13.5409f, 11.3917f, 13.3529f, 11.425f)
                    curveTo(12.367f, 11.5899f, 9.3577f, 12.2472f, 8.1603f, 14.3455f)
                    curveTo(7.1825f, 16.039f, 7.6608f, 18.158f, 9.2324f, 19.0654f)
                    curveTo(10.804f, 19.9727f, 12.876f, 19.3312f, 13.8538f, 17.6377f)
                    curveTo(15.0477f, 15.5778f, 14.1263f, 12.6106f, 13.7818f, 11.6726f)
                    close()
                }
                path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                        strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                        pathFillType = EvenOdd) {
                    moveTo(13.2265f, 10.6857f)
                    curveTo(13.7713f, 10.5914f, 14.2968f, 10.8956f, 14.4863f, 11.4153f)
                    curveTo(14.6696f, 11.9147f, 15.0068f, 12.9531f, 15.1401f, 14.1516f)
                    curveTo(15.2719f, 15.337f, 15.216f, 16.7826f, 14.503f, 18.0132f)
                    curveTo(13.3453f, 20.0176f, 10.8332f, 20.8556f, 8.8574f, 19.7149f)
                    curveTo(6.8809f, 18.5738f, 6.3542f, 15.9756f, 7.5098f, 13.9721f)
                    curveTo(8.2234f, 12.7228f, 9.4436f, 11.9493f, 10.536f, 11.4727f)
                    curveTo(11.638f, 10.992f, 12.7019f, 10.7736f, 13.2265f, 10.6857f)
                    close()
                }
                path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                        strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                        pathFillType = NonZero) {
                    moveTo(7.8504f, 4.7098f)
                    curveTo(7.7988f, 4.5679f, 7.6598f, 4.4876f, 7.5112f, 4.5139f)
                    curveTo(6.7315f, 4.6443f, 4.3514f, 5.1642f, 3.4044f, 6.8237f)
                    curveTo(2.6311f, 8.1631f, 3.0094f, 9.839f, 4.2523f, 10.5566f)
                    curveTo(5.4953f, 11.2742f, 7.134f, 10.7668f, 7.9073f, 9.4274f)
                    curveTo(8.8515f, 7.7983f, 8.1228f, 5.4516f, 7.8504f, 4.7098f)
                    close()
                }
                path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                        strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                        pathFillType = EvenOdd) {
                    moveTo(8.555f, 4.4524f)
                    curveTo(8.7023f, 4.8538f, 8.9733f, 5.688f, 9.0806f, 6.653f)
                    curveTo(9.1865f, 7.605f, 9.1448f, 8.7878f, 8.5565f, 9.8031f)
                    curveTo(7.6034f, 11.4533f, 5.5245f, 12.157f, 3.8774f, 11.2061f)
                    curveTo(2.2297f, 10.2547f, 1.8029f, 8.0997f, 2.7541f, 6.4503f)
                    curveTo(3.3425f, 5.4204f, 4.3427f, 4.791f, 5.2208f, 4.4079f)
                    curveTo(6.1084f, 4.0207f, 6.9635f, 3.8453f, 7.385f, 3.7746f)
                    curveTo(7.8904f, 3.6873f, 8.3792f, 3.9703f, 8.555f, 4.4524f)
                    close()
                }
                path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                        strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                        pathFillType = NonZero) {
                    moveTo(21.2643f, 8.07f)
                    curveTo(21.2201f, 7.9484f, 21.1009f, 7.8796f, 20.9735f, 7.9021f)
                    curveTo(20.3052f, 8.0139f, 18.2653f, 8.4594f, 17.4536f, 9.8818f)
                    curveTo(16.7909f, 11.0298f, 17.1151f, 12.4662f, 18.1804f, 13.0812f)
                    curveTo(19.2457f, 13.6963f, 20.6502f, 13.2614f, 21.313f, 12.1135f)
                    curveTo(22.1223f, 10.7172f, 21.4977f, 8.7058f, 21.2643f, 8.07f)
                    close()
                }
                path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                        strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                        pathFillType = EvenOdd) {
                    moveTo(21.9687f, 7.8127f)
                    curveTo(22.0966f, 8.161f, 22.3318f, 8.8849f, 22.4251f, 9.7237f)
                    curveTo(22.5169f, 10.5494f, 22.483f, 11.59f, 21.9624f, 12.4888f)
                    curveTo(21.1198f, 13.9478f, 19.275f, 14.5792f, 17.8054f, 13.7308f)
                    curveTo(16.3352f, 12.882f, 15.9625f, 10.9664f, 16.8032f, 9.5084f)
                    curveTo(17.3239f, 8.5971f, 18.2053f, 8.0455f, 18.9675f, 7.7131f)
                    curveTo(19.7393f, 7.3764f, 20.4815f, 7.2241f, 20.8473f, 7.1628f)
                    curveTo(21.3314f, 7.0793f, 21.8003f, 7.3508f, 21.9687f, 7.8127f)
                    close()
                }
            }
        }
        .build()
        return _drops!!
    }

private var _drops: ImageVector? = null
