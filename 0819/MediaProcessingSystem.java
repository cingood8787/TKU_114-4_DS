public class MediaProcessingSystem {

    // 播放能力
    interface Playable {
        void play();
    }

    // 壓縮能力
    interface Compressible {
        void compress();
    }

    // 抽象父類別
    static abstract class MediaFile {
        private String fileName;

        public MediaFile(String fileName) {
            this.fileName = fileName;
        }

        public String getFileName() {
            return fileName;
        }

        // 每種媒體檔案都有自己的處理方式
        public abstract void process();
    }

    // 圖片
    static class ImageFile extends MediaFile
            implements Compressible {

        public ImageFile(String fileName) {
            super(fileName);
        }

        @Override
        public void process() {
            System.out.println(
                    getFileName() + "：處理圖片檔案"
            );
        }

        @Override
        public void compress() {
            System.out.println(
                    getFileName() + "：圖片壓縮完成"
            );
        }
    }

    // 音訊
    static class AudioFile extends MediaFile
            implements Playable, Compressible {

        public AudioFile(String fileName) {
            super(fileName);
        }

        @Override
        public void process() {
            System.out.println(
                    getFileName() + "：處理音訊檔案"
            );
        }

        @Override
        public void play() {
            System.out.println(
                    getFileName() + "：正在播放音訊"
            );
        }

        @Override
        public void compress() {
            System.out.println(
                    getFileName() + "：音訊壓縮完成"
            );
        }
    }

    // 影片
    static class VideoFile extends MediaFile
            implements Playable, Compressible {

        public VideoFile(String fileName) {
            super(fileName);
        }

        @Override
        public void process() {
            System.out.println(
                    getFileName() + "：處理影片檔案"
            );
        }

        @Override
        public void play() {
            System.out.println(
                    getFileName() + "：正在播放影片"
            );
        }

        @Override
        public void compress() {
            System.out.println(
                    getFileName() + "：影片壓縮完成"
            );
        }
    }

    public static void main(String[] args) {

        // 使用 MediaFile[] 保存不同媒體物件
        MediaFile[] files = {
                new ImageFile("photo.jpg"),
                new AudioFile("music.mp3"),
                new VideoFile("movie.mp4"),
                new ImageFile("picture.png")
        };

        System.out.println("=== 媒體檔案處理 ===");

        for (MediaFile file : files) {

            System.out.println(
                    "\n檔案：" + file.getFileName()
            );

            // Polymorphism
            file.process();

            // 判斷是否支援播放
            if (file instanceof Playable playable) {
                playable.play();
            } else {
                System.out.println("此檔案不支援播放");
            }

            // 判斷是否支援壓縮
            if (file instanceof Compressible compressible) {
                compressible.compress();
            } else {
                System.out.println("此檔案不支援壓縮");
            }
        }
    }
}