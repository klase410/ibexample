public class Item{
    int _id;
    String _title;
    int _status;

    public Item(int id, int status, String title) {
        _id = id;
        _title = title;
        _status = status;
    }

    public String getTitle(){
        return _title;
    }

    public void setTitle(String title) {
        this._title = title;
    }

    public int getStatus(){
        return this._status;
    }

    public void setStatus(int status) {
        this._status = status;
    }

    public int getId(){
        return this._id;
    }
}