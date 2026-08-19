public class MessageSenderSystem {

    // MessageSender Interface
    interface MessageSender {
        void send(String receiver, String message);
    }

    // EmailSender
    static class EmailSender implements MessageSender {

        @Override
        public void send(String receiver, String message) {

            if (receiver == null || receiver.trim().isEmpty()
                    || message == null || message.trim().isEmpty()) {

                System.out.println("Email 發送失敗：收件者或訊息不可空白");
                return;
            }

            System.out.println(
                    "Email 發送給 " + receiver
                    + "：" + message
            );
        }
    }

    // SmsSender
    static class SmsSender implements MessageSender {

        @Override
        public void send(String receiver, String message) {

            if (receiver == null || receiver.trim().isEmpty()
                    || message == null || message.trim().isEmpty()) {

                System.out.println("SMS 發送失敗：收件者或訊息不可空白");
                return;
            }

            System.out.println(
                    "SMS 發送給 " + receiver
                    + "：" + message
            );
        }
    }

    // ConsoleSender
    static class ConsoleSender implements MessageSender {

        @Override
        public void send(String receiver, String message) {

            if (receiver == null || receiver.trim().isEmpty()
                    || message == null || message.trim().isEmpty()) {

                System.out.println("Console 發送失敗：收件者或訊息不可空白");
                return;
            }

            System.out.println(
                    "Console 訊息給 " + receiver
                    + "：" + message
            );
        }
    }

    // 只依賴 MessageSender
    public static void notify(
            MessageSender sender,
            String receiver,
            String message) {

        if (sender == null) {
            System.out.println("發送失敗：Sender 不可為 null");
            return;
        }

        sender.send(receiver, message);
    }

    public static void main(String[] args) {

        MessageSender emailSender =
                new EmailSender();

        MessageSender smsSender =
                new SmsSender();

        MessageSender consoleSender =
                new ConsoleSender();

        System.out.println("=== 正常發送 ===");

        notify(
                emailSender,
                "student@gmail.com",
                "作業已繳交"
        );

        notify(
                smsSender,
                "0912345678",
                "上課時間到了"
        );

        notify(
                consoleSender,
                "王小明",
                "系統測試訊息"
        );

        // 測試空白 receiver
        System.out.println("\n=== 空白 Receiver 測試 ===");

        notify(
                emailSender,
                "",
                "測試訊息"
        );

        // 測試空白 message
        System.out.println("\n=== 空白 Message 測試 ===");

        notify(
                smsSender,
                "0912345678",
                ""
        );
    }
}