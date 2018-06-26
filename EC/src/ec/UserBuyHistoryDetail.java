package ec;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.BuyDataBeans;
import beans.ItemDataBeans;
import dao.BuyDAO;
import dao.BuyDetailDAO;

/**
 * 購入履歴画面
 * @author d-yamaguchi
 *
 */
@WebServlet("/UserBuyHistoryDetail")
public class UserBuyHistoryDetail extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		try {

			// ボタンを押した時に取得したt_buy.IDをgetParameterから取得
			String id = request.getParameter("id");

			//文字列を整数に変換する
			int BuyId =  Integer.parseInt(id);

			//getParameterの値を引数にDAOのメソッドを呼び出し、sql文を実行する。
			ArrayList<ItemDataBeans> buyDetailItemList= BuyDetailDAO.getItemDataBeansListByBuyId(BuyId);

			//buyDetailItemListインスタンスをリクエストスコープにセットし、jspで使用する
			request.setAttribute("buyDetailItemList",buyDetailItemList);

			//取得したユーザIDを引数に渡し、DAOのメソッドからArrayListに入ったインスタンスを取りだす。
			ArrayList<BuyDataBeans> BDB=BuyDAO.getBuyDataBeansById(BuyId);

			//jspでArrayListのインスタンスを使用するためにリクエストスコープへセットする。
			request.setAttribute("BDB", BDB);

			//buyhistorydetail.jspへフォワードする
			request.getRequestDispatcher(EcHelper.USER_BUY_HISTORY_DETAIL_PAGE).forward(request, response);
		}catch (Exception e) {
			e.printStackTrace();
			session.setAttribute("errorMessage", e.toString());
			response.sendRedirect("Error");
		}
	}


}
