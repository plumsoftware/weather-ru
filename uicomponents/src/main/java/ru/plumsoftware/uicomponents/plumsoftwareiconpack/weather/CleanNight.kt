package ru.plumsoftware.uicomponents.plumsoftwareiconpack.weather

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.EvenOdd
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import ru.plumsoftware.uicomponents.plumsoftwareiconpack.WeatherGroup

public val WeatherGroup.CleanNight: ImageVector
    get() {
        if (_clean_night != null) {
            return _clean_night!!
        }
        _clean_night = Builder(name = "Clean night", defaultWidth = 24.0.dp, defaultHeight =
                24.0.dp, viewportWidth = 24.0f, viewportHeight = 24.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = EvenOdd) {
                moveTo(10.3218f, 4.4956f)
                curveTo(10.8836f, 5.0056f, 10.7451f, 5.7832f, 10.492f, 6.3444f)
                lineTo(10.4915f, 6.3455f)
                curveTo(10.1188f, 7.168f, 9.9144f, 8.0684f, 9.922f, 9.0202f)
                lineTo(9.922f, 9.0227f)
                curveTo(9.9371f, 12.5473f, 12.9798f, 15.5948f, 16.7542f, 15.7439f)
                curveTo(17.3102f, 15.7663f, 17.8421f, 15.7291f, 18.358f, 15.6399f)
                curveTo(18.7005f, 15.5797f, 19.0353f, 15.5692f, 19.3366f, 15.637f)
                curveTo(19.6417f, 15.7057f, 19.9567f, 15.8676f, 20.1553f, 16.1814f)
                curveTo(20.3554f, 16.4977f, 20.3648f, 16.8532f, 20.2894f, 17.1592f)
                curveTo(20.2157f, 17.4587f, 20.0546f, 17.749f, 19.8478f, 18.0197f)
                curveTo(18.0495f, 20.3951f, 15.0643f, 21.8902f, 11.7422f, 21.7511f)
                curveTo(7.0316f, 21.5511f, 3.0545f, 17.9076f, 2.7248f, 13.3585f)
                curveTo(2.4236f, 9.3266f, 4.9026f, 5.8341f, 8.4729f, 4.3804f)
                curveTo(9.0454f, 4.1462f, 9.7987f, 4.0207f, 10.3218f, 4.4956f)
                close()
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(19.9957f, 5.6842f)
                lineTo(20.5655f, 5.7979f)
                curveTo(20.5752f, 5.7998f, 20.5752f, 5.8137f, 20.5655f, 5.8157f)
                lineTo(19.9957f, 5.9293f)
                curveTo(18.9186f, 6.1441f, 18.0763f, 6.9855f, 17.8604f, 8.0623f)
                lineTo(17.7473f, 8.6262f)
                curveTo(17.7454f, 8.6359f, 17.7315f, 8.6359f, 17.7295f, 8.6262f)
                lineTo(17.6164f, 8.0623f)
                curveTo(17.4005f, 6.9855f, 16.5582f, 6.1441f, 15.4812f, 5.9293f)
                lineTo(14.9113f, 5.8157f)
                curveTo(14.9016f, 5.8137f, 14.9016f, 5.7998f, 14.9113f, 5.7979f)
                lineTo(15.4812f, 5.6842f)
                curveTo(16.5582f, 5.4694f, 17.4005f, 4.6281f, 17.6164f, 3.5512f)
                lineTo(17.7295f, 2.9874f)
                curveTo(17.7315f, 2.9777f, 17.7454f, 2.9777f, 17.7473f, 2.9874f)
                lineTo(17.8604f, 3.5512f)
                curveTo(18.0764f, 4.6281f, 18.9186f, 5.4694f, 19.9957f, 5.6842f)
                close()
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = EvenOdd) {
                moveTo(17.0627f, 2.8537f)
                curveTo(17.2107f, 2.1155f, 18.2661f, 2.1154f, 18.4141f, 2.8537f)
                lineTo(18.5272f, 3.4175f)
                curveTo(18.6892f, 4.2251f, 19.3209f, 4.8562f, 20.1287f, 5.0173f)
                lineTo(20.6985f, 5.1309f)
                curveTo(21.4377f, 5.2783f, 21.4377f, 6.3352f, 20.6985f, 6.4827f)
                lineTo(20.1287f, 6.5963f)
                curveTo(19.3209f, 6.7574f, 18.6892f, 7.3885f, 18.5272f, 8.1961f)
                lineTo(18.4141f, 8.7599f)
                curveTo(18.2661f, 9.4981f, 17.2107f, 9.4981f, 17.0627f, 8.7599f)
                lineTo(16.9496f, 8.1961f)
                curveTo(16.7876f, 7.3885f, 16.1559f, 6.7574f, 15.3481f, 6.5963f)
                lineTo(14.7783f, 6.4827f)
                curveTo(14.0391f, 6.3352f, 14.0391f, 5.2783f, 14.7783f, 5.1309f)
                lineTo(15.3481f, 5.0173f)
                curveTo(16.1559f, 4.8562f, 16.7876f, 4.2251f, 16.9496f, 3.4175f)
                lineTo(17.0627f, 2.8537f)
                close()
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = EvenOdd) {
                moveTo(15.2808f, 9.2671f)
                curveTo(15.3954f, 8.6944f, 16.2123f, 8.6944f, 16.3269f, 9.2671f)
                lineTo(16.4145f, 9.7045f)
                curveTo(16.5399f, 10.331f, 17.0288f, 10.8206f, 17.6541f, 10.9456f)
                lineTo(18.0952f, 11.0337f)
                curveTo(18.6674f, 11.1481f, 18.6674f, 11.9681f, 18.0952f, 12.0824f)
                lineTo(17.6541f, 12.1706f)
                curveTo(17.0288f, 12.2956f, 16.5399f, 12.7851f, 16.4145f, 13.4117f)
                lineTo(16.3269f, 13.8491f)
                curveTo(16.2123f, 14.4218f, 15.3954f, 14.4218f, 15.2808f, 13.8491f)
                lineTo(15.1932f, 13.4117f)
                curveTo(15.0679f, 12.7851f, 14.5789f, 12.2956f, 13.9536f, 12.1706f)
                lineTo(13.5125f, 12.0824f)
                curveTo(12.9403f, 11.9681f, 12.9403f, 11.1481f, 13.5125f, 11.0337f)
                lineTo(13.9536f, 10.9456f)
                curveTo(14.5789f, 10.8206f, 15.0679f, 10.331f, 15.1932f, 9.7045f)
                lineTo(15.2808f, 9.2671f)
                close()
            }
        }
        .build()
        return _clean_night!!
    }

private var _clean_night: ImageVector? = null
