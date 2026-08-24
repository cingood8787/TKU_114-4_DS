class Result<T> {
    private boolean success;
    private String message;
    private T data;

    public Result(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }
}

public class GenericResultDemo {

    public static void main(String[] args) {

        // Result<String>
        Result<String> stringResult =
                new Result<>(true, "取得姓名成功", "Amy");

        System.out.println("成功：" + stringResult.isSuccess());
        System.out.println("訊息：" + stringResult.getMessage());
        System.out.println("資料：" + stringResult.getData());

        // 不需要 cast
        String name = stringResult.getData();
        System.out.println("姓名：" + name);


        System.out.println("----------------");


        // Result<Integer>
        Result<Integer> integerResult =
                new Result<>(true, "取得分數成功", 90);

        System.out.println("成功：" + integerResult.isSuccess());
        System.out.println("訊息：" + integerResult.getMessage());
        System.out.println("資料：" + integerResult.getData());

        // 不需要 cast
        Integer score = integerResult.getData();
        System.out.println("分數：" + score);


        System.out.println("----------------");


        // 失敗情況，data == null
        Result<String> failedResult =
                new Result<>(false, "找不到資料", null);

        System.out.println("成功：" + failedResult.isSuccess());
        System.out.println("訊息：" + failedResult.getMessage());

        if (failedResult.getData() == null) {
            System.out.println("資料為 null");
        }

        // 錯誤型態會在編譯階段被發現
        // Result<String> wrong =
        //         new Result<>(true, "錯誤測試", 100);
    }
}