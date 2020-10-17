import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import listener.Condition;
import listener.NormalUserListener;
import pojo.NormalUser;
import pojo.PageResponse;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class FindServlet extends HttpServlet {

    private NormalUserListener listener = new NormalUserListener();
    private Map<Condition, List<NormalUser>> cache = new HashMap<>();

    @Override
    protected void doGet(HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        final int page = Integer.parseInt(req.getParameter("page"));
        final int size = Integer.parseInt(req.getParameter("size"));
        String[] keys = req.getParameterValues("key");
        String[] values = req.getParameterValues("value");
        final String s = req.getParameter("size");
        InputStream is = getServletContext().getResourceAsStream("/WEB-INF/contract/list.xlsx");
        final Condition condition = new Condition();
        if (keys.length!=values.length){
            resp.sendError(400);
        }
        for (int i = 0;i<keys.length;i++){
            condition.add(keys[i], values[i]);
            Logger.getAnonymousLogger().info(keys[i]+":"+values[i]);
        }
        if (cache.containsKey(condition)) {
            List<NormalUser> total = cache.get(condition);
            doPage(total, page, size, resp);
        }
        else{
            listener.setCondition(condition);
            listener.setCallBack(new NormalUserListener.CallBack() {
                @Override
                public void onFinished(List<NormalUser> users) throws IOException {
                    cache.put(condition, users);
                    doPage(users, page, size, resp);
                }
            });
            EasyExcel.read(is, NormalUser.class, listener).sheet().doRead();
        }
    }

    private void doPage(List<NormalUser> users, int page, int size, HttpServletResponse resp) throws IOException {
        if (page*size<users.size()&&(page*size+size)<users.size()){
            PageResponse res = new PageResponse(users.subList(page*size, page*size+size), users.size());
            resp.getWriter().print(JSON.toJSON(res));
        }else if (page*size<users.size()&&(page*size+size)>=users.size()){
            PageResponse res = new PageResponse(users.subList(page*size, users.size()), users.size());
            resp.getWriter().print(JSON.toJSON(res));
        }
    }
}
