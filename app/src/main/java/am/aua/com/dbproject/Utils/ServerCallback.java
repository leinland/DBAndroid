package am.aua.com.dbproject.Utils;

import java.util.HashMap;
import java.util.List;

public interface ServerCallback {
    void onSuccess(HashMap<String, List<SubCategory>> result);
}
