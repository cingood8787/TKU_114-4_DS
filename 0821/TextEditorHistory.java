import java.util.ArrayDeque;
import java.util.Deque;

public class TextEditorHistory {

    private Deque<String> undoStack = new ArrayDeque<>();
    private Deque<String> redoStack = new ArrayDeque<>();

    // 新增操作
    public void addOperation(String text) {
        undoStack.push(text);

        // 新增操作後，redo 必須清空
        redoStack.clear();

        System.out.println("新增操作：" + text);
        printState();
    }

    // Undo
    public void undo() {
        if (undoStack.isEmpty()) {
            System.out.println("Undo stack 為空，無法 undo");
            printState();
            return;
        }

        String operation = undoStack.pop();
        redoStack.push(operation);

        System.out.println("Undo：" + operation);
        printState();
    }

    // Redo
    public void redo() {
        if (redoStack.isEmpty()) {
            System.out.println("Redo stack 為空，無法 redo");
            printState();
            return;
        }

        String operation = redoStack.pop();
        undoStack.push(operation);

        System.out.println("Redo：" + operation);
        printState();
    }

    // 顯示目前狀態
    public void printState() {
        System.out.println("Undo Stack：" + undoStack);
        System.out.println("Redo Stack：" + redoStack);
        System.out.println("----------------------------");
    }

    public static void main(String[] args) {

        TextEditorHistory editor = new TextEditorHistory();

        editor.addOperation("輸入 Hello");
        editor.addOperation("輸入 World");
        editor.addOperation("刪除 World");

        editor.undo();
        editor.undo();

        editor.redo();

        // 新操作後 redo stack 會被清空
        editor.addOperation("輸入 Java");

        // redo 已被清空
        editor.redo();

        // 再測試空 undo
        editor.undo();
        editor.undo();
        editor.undo();
        editor.undo();
    }
}