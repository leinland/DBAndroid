package am.aua.com.dbproject.Utils;

public class BorrowsItems{
    private int user_id;
    private int book_id;
    private long take_date;
    private long return_date;
    private boolean return_status;

    public BorrowsItems(int userId, int bookId, int borrowDate, int returnDate, boolean checkBox) {
        this.user_id = userId;
        this.book_id = bookId;
        this.take_date = borrowDate;
        this.return_date = returnDate;
        this.return_status = checkBox;
    }

    public int getUserId() {
        return user_id;
    }

    public void setUserId(int userId) {
        this.user_id = userId;
    }

    public int getBookId() {
        return book_id;
    }

    public void setBookId(int bookId) {
        this.book_id = bookId;
    }

    public long getTake_date() {
        return take_date;
    }

    public void setTake_date(long take_date) {
        this.take_date = take_date;
    }

    public long getReturn_date() {
        return return_date;
    }

    public void setReturn_date(long return_date) {
        this.return_date = return_date;
    }

    public boolean isReturn_status() {
        return return_status;
    }

    public void setReturn_status(boolean return_status) {
        this.return_status = return_status;
    }
}
