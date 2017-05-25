package com.waynell.videolist.demo.util;

import com.waynell.videolist.demo.model.BaseItem;
import com.waynell.videolist.demo.model.PicItem;
import com.waynell.videolist.demo.model.TextItem;
import com.waynell.videolist.demo.model.VideoItem;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Wayne
 */
public class ItemUtils {

    private static final String VIDEO_URL1 = "http://www.sample-videos.com/video/mp4/720/big_buck_bunny_720p_1mb.mp4";
    private static final String VIDEO_URL2 = "http://techslides.com/demos/sample-videos/small.mp4";
    private static final String VIDEO_URL3 = "http://7xl1ve.com5.z0.glb.qiniucdn.com/2017/05/23/18/15/c13ecd1c5e764de0b4715b23721174df.mp4";
    private static final String VIDEO_URL4 = "http://7xl1ve.com5.z0.glb.qiniucdn.com/2017/05/20/14/14/f80a41347a954d12903b4fe9494887c3.mp4";
    private static final String VIDEO_URL6 = "http://7xl1ve.com5.z0.glb.qiniucdn.com/2017/05/22/09/24/14e40553476d477f9672dd981bb8a500.mp4";
    private static final String VIDEO_URL7 = "http://7xl1ve.com5.z0.glb.qiniucdn.com/2017/05/22/09/34/6b4a7105289344be9d422f64db3f15cd.mp4";
    private static final String VIDEO_URL8 = "http://7xl1ve.com5.z0.glb.qiniucdn.com/2017/05/22/20/17/99e3f8ac7a5740a8a3e789106ea5ecc8/90.mp4";
    private static final String VIDEO_URL9 = "http://7xl1ve.com5.z0.glb.qiniucdn.com/2017/04/10/21/35/d18bf03251af4d5a99f1d171116de02e.mp4";
    private static final String VIDEO_URL10 = "http://7xl1ve.com5.z0.glb.qiniucdn.com/2017/04/13/07/19/b712aff78e6b4d6699268c8168756569.mp4";
    private static final String VIDEO_URL11 = "http://7xl1ve.com5.z0.glb.qiniucdn.com/2017/04/12/12/35/a8b32348f0ce44b7a2b16df5e7bbe2ed.mp4";
    private static final String VIDEO_URL12 = "http://7xl1ve.com5.z0.glb.qiniucdn.com/2017/04/10/18/18/e6da7671d058403f8a51e05bcfa0d40c.mp4";

    private static final String PIC_URL1 = "http://img10.3lian.com/sc6/show02/67/27/03.jpg";
    private static final String PIC_URL2 = "http://img10.3lian.com/sc6/show02/67/27/04.jpg";
    private static final String PIC_URL3 = "http://img10.3lian.com/sc6/show02/67/27/01.jpg";
    private static final String PIC_URL4 = "http://img10.3lian.com/sc6/show02/67/27/02.jpg";

    public static List<BaseItem> generateMockData() {
        List<BaseItem> list = new ArrayList<>();

        //list.add(new TextItem("TextItem"));
        //list.add(new PicItem(PIC_URL1));
        list.add(new VideoItem(VIDEO_URL4, PIC_URL4));

        //list.add(new TextItem("TextItem"));
        //list.add(new PicItem(PIC_URL2));
        list.add(new VideoItem(VIDEO_URL3, PIC_URL3));

        //list.add(new TextItem("TextItem"));
        //list.add(new PicItem(PIC_URL3));
        list.add(new VideoItem(VIDEO_URL2, PIC_URL2));

        //list.add(new TextItem("TextItem"));
        //list.add(new PicItem(PIC_URL4));
        list.add(new VideoItem(VIDEO_URL1, PIC_URL1));

        //list.add(new TextItem("TextItem"));
        //list.add(new PicItem(PIC_URL1));

        //list.add(new TextItem("TextItem"));
        //list.add(new PicItem(PIC_URL2));
        list.add(new VideoItem(VIDEO_URL6, PIC_URL3));
//
        //list.add(new TextItem("TextItem"));
        //list.add(new PicItem(PIC_URL3));
        list.add(new VideoItem(VIDEO_URL7, PIC_URL2));
        list.add(new VideoItem(VIDEO_URL8, PIC_URL2));
        list.add(new VideoItem(VIDEO_URL9, PIC_URL2));
        list.add(new VideoItem(VIDEO_URL10, PIC_URL2));
        list.add(new VideoItem(VIDEO_URL11, PIC_URL2));
        list.add(new VideoItem(VIDEO_URL12, PIC_URL2));

        //list.add(new TextItem("TextItem"));
        //list.add(new PicItem(PIC_URL4));

        return list;
    }

}
