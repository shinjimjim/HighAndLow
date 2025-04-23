package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

//表示クラス
public class Display implements ActionListener
{
    //画面全体のオブジェクト
    private JFrame disp;

    //パネルオブジェクト（上部／中央部／下部）
    private JPanel top_panel, mid_panel, bottom_Panel;

    //メッセージ表示ラベルオブジェクト
    private JLabel msg_lbl;

    //親のカードクラスラベルオブジェクト（マーク、数字）
    private JLabel parent_lbl, parent_suit_lbl, parent_no_lbl;

    //子のカードクラスラベルオブジェクト（マーク、数字）
    private JLabel child_lbl, child_suit_lbl, child_no_lbl;

    //ボタンオブジェクト（HIGH／LOW）
    private JButton btn_high, btn_low;

    //プレイヤーオブジェクト（親、子）
    private Player parent, child;
    
    private int score = 0;      // スコアカウンター
    private JLabel score_lbl;   // スコアを表示するラベル

    //コンストラクタ（初期化処理）
    public Display(Player prn, Player chl)
    {
        //メインクラスから受け取った親と子のオブジェクトを設定
        parent = prn;
        child = chl;

        //ゲーム画面全体の表示設定
        disp = new JFrame("HIGH & LOW");                    //画面を生成
        disp.setSize(480, 280);                             //表示サイズを設定
        disp.setLocationRelativeTo(null);                   //画面の表示位置を中央に設定
        disp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//「×」ボタンで画面を閉じるように設定
        disp.setResizable(false);                           //画面サイズを変更できないように設定

        //トップパネルの表示設定
        top_panel = new JPanel();//パネルを生成
        setPanel(top_panel, Color.RED, null, new Dimension(480, 50));//パネルの背景色、レイアウト、サイズを設定
        disp.add(top_panel, BorderLayout.NORTH); 						//画面上部にパネルを追加

        //メッセージラベルを表示
        msg_lbl = new JLabel("一発勝負！HIGHかLOWか当ててください。");     //ラベルを生成
        top_panel.add(msg_lbl);                                       //トップパネルに追加
        setLabelFont(msg_lbl, Color.WHITE, 0, 15, 480, 20, 20, false);//ラベルのフォント設定
        msg_lbl.setForeground(Color.WHITE);                           //文字の色を設定

        //ミドルパネルの表示設定
        mid_panel = new JPanel();                                     //パネルを生成
        setPanel(mid_panel, Color.GREEN, null, new Dimension(480, 180));//パネルの背景色、レイアウト、サイズを設定
        disp.add(mid_panel, BorderLayout.CENTER);                     //画面中央部にパネルを追加

        //親カードの情報を表示
        parent_lbl = new JLabel("私のカード");
        parent_suit_lbl = new JLabel(getSuitIcon(parent.GetSuit()));//マークを表示するラベル
        parent_no_lbl = new JLabel(getNoStr(parent.GetNo()));       //数字を表示するラベル
        //ミドルパネルに追加
        mid_panel.add(parent_lbl);
        mid_panel.add(parent_suit_lbl);
        mid_panel.add(parent_no_lbl);
        //ラベルのフォント設定
        setLabelFont(parent_lbl, Color.WHITE, 90,10,100,20,14,false);
        setLabelFont(parent_suit_lbl, Color.WHITE, 100, 10, 80, 100, 16, false);
        setLabelFont(parent_no_lbl, Color.WHITE, 100, 35, 80, 100, 16, true);
        
        //子カードの情報を表示
        child_lbl = new JLabel("あなたのカード");
        child_suit_lbl = new JLabel("");
        child_no_lbl = new JLabel("?");
        //ミドルパネルに追加
        mid_panel.add(child_lbl);
        mid_panel.add(child_suit_lbl);
        mid_panel.add(child_no_lbl);
        //ラベルのフォント設定
        setLabelFont(child_lbl, Color.WHITE, 265, 10, 150, 20, 14, false);
        setLabelFont(child_suit_lbl, Color.LIGHT_GRAY, 300, 10, 80, 100, 16, false);
        setLabelFont(child_no_lbl, Color.LIGHT_GRAY, 300, 35, 80, 100, 16, true);
        
        //ボトムパネルの表示設定
        bottom_Panel = new JPanel();
        setPanel(bottom_Panel, Color.LIGHT_GRAY, new BorderLayout(), new Dimension(480, 50));//パネルの背景色、レイアウト、サイズを設定
        disp.add(bottom_Panel, BorderLayout.SOUTH); 										 //画面下部にパネルを追加
        
        //HIGHボタンを表示
        btn_high = new JButton("HIGH");
        setButton(btn_high, this, 240, 50, 20);		  //ボタンのフォントやイベント設定
        bottom_Panel.add(btn_high, BorderLayout.WEST);// ボトムパネル左側にボタンを追加
        btn_high.setBackground(Color.LIGHT_GRAY);     //ボタンの色を設定
        btn_high.setOpaque(true);
        
        //LOWボタンを表示
        btn_low = new JButton("LOW");
        setButton(btn_low, this, 240, 50, 20);       // ボタンのフォントやイベント設定
        bottom_Panel.add(btn_low, BorderLayout.EAST);// ボトムパネル右側にボタンを追加
        btn_low.setBackground(Color.LIGHT_GRAY);     //ボタンの色を設定
        btn_low.setOpaque(true);
        
        //ゲーム画面を表示
        disp.setVisible(true);
        
        // スコア表示ラベル
        score_lbl = new JLabel("スコア: 0");
        score_lbl.setForeground(Color.YELLOW);
        setLabelFont(score_lbl, Color.YELLOW, 190, 20, 100, 20, 16, false);
        mid_panel.add(score_lbl);

    }
    
    //HIGHかLOWが選択されたときのイベント
    public void actionPerformed(ActionEvent e)
    {
    	String cmd = e.getActionCommand();//アクションコマンド(どのボタンが押されたか)
    	int parent_no = parent.GetNo();   //親カードの数字
    	int child_no  = child.GetNo();    //子カードの数字
    	int child_suit = child.GetSuit(); //子カードのマーク
    	
    	//子のカードをオープン
    	child_no_lbl.setBackground(Color.WHITE);        //数字の背景色
    	child_no_lbl.setText(getNoStr(child.GetNo()));  //数字の表示データ
    	child_suit_lbl.setBackground(Color.WHITE);      //マークの背景色
    	child_suit_lbl.setIcon(getSuitIcon(child_suit));//マークの表示データ
    	
    	//押されたボタンに応じた処理を行う
    	if( cmd.equals("HIGH") ) //HIGHボタンが押された時の処理
    	{
    		//ボタンの色を変える
    		btn_high.setBackground(Color.GRAY);
    		btn_high.setOpaque(true);
    		
    		//結果を判定してメッセージ更新
    		if( parent_no < child_no ) //子の方が大きい
    			msg_lbl.setText("大正解、あなたの勝ちです！");
    		else if( child_no < parent_no ) //親の方が大きい
    			msg_lbl.setText("不正解、あなたの負けです！");
    		else //親と子の数字が同じ
    			msg_lbl.setText("奇遇ですね。引き分けです！");
    	}
    	else if( cmd.equals("LOW") ) //LOWボタンが押された時の処理
    	{
    		btn_low.setBackground(Color.GRAY); //ボタンの色を変える
    		btn_low.setOpaque(true);
    		
    		//結果を判定してメッセージ更新
    		if( parent_no < child_no ) //子の方が大きい
    			msg_lbl.setText("不正解、あなたの負けです！");
    		else if( child_no < parent_no ) //親の方が大きい
    			msg_lbl.setText("大正解、あなたの勝ちです！");
    		else //親と子の数字が同じ
    			msg_lbl.setText("奇遇ですね。引き分けです！");
    	}
    	
    	//勝敗の判定部分でスコア更新
    	if (parent_no < child_no) // HIGHで勝ち
    	{ 
    	    if (cmd.equals("HIGH")) 
    	    {
    	        msg_lbl.setText("大正解、あなたの勝ちです！");
    	        updateScore(true); // 勝ったらスコア更新
    	    } 
    	    else 
    	    {
    	        msg_lbl.setText("不正解、あなたの負けです！");
    	        updateScore(false);
    	    }
    	}
    	if (parent_no > child_no) // LOWで勝ち
    	{ 
    	    if (cmd.equals("LOW")) 
    	    {
    	        msg_lbl.setText("大正解、あなたの勝ちです！");
    	        updateScore(true);
    	    } 
    	    else 
    	    {
    	        msg_lbl.setText("不正解、あなたの負けです！");
    	        updateScore(false);
    	    }
    	}
    	
    	if (cmd.equals("HIGH") || cmd.equals("LOW")) 
    	{
    		child_no_lbl.setText(getNoStr(child.GetNo())); // 数字を表示
            child_suit_lbl.setIcon(getSuitIcon(child_suit)); // マークを表示

    	    // 押されたボタンが HIGH か LOW の時だけ音を鳴らす
    	    if (parent_no < child_no && cmd.equals("HIGH")) 
    	    {
    	        msg_lbl.setText("大正解、あなたの勝ちです！");
    	        SoundPlayer.playSound("win.wav"); // 勝ちの音
    	    } 
    	    else if (child_no < parent_no && cmd.equals("HIGH")) 
    	    {
    	        msg_lbl.setText("不正解、あなたの負けです！");
    	        SoundPlayer.playSound("lose.wav"); // 負けの音
    	    }
    	    else if (parent_no > child_no && cmd.equals("LOW")) 
    	    {
    	        msg_lbl.setText("大正解、あなたの勝ちです！");
    	        SoundPlayer.playSound("win.wav"); // 勝ちの音
    	    }
    	    else if (child_no > parent_no && cmd.equals("LOW")) 
    	    {
    	        msg_lbl.setText("不正解、あなたの負けです！");
    	        SoundPlayer.playSound("lose.wav"); // 負けの音
    	    }
    	    else if (parent_no == child_no) 
    	    {
    	        msg_lbl.setText("奇遇ですね。引き分けです！");
    	        SoundPlayer.playSound("draw.wav"); // 引き分けの音
    	    }
    	    
    	    // 🔥 0.5秒後にメッセージを表示
            Timer msgTimer = new Timer(500, new ActionListener() 
            {
                @Override
                public void actionPerformed(ActionEvent e) 
                {
                    msg_lbl.setText(msg_lbl.getText());

                    btn_high.setText("もう一回"); // ボタンの名前を変える
                    btn_low.setText("終了");
                }
            });
            msgTimer.setRepeats(false);
            msgTimer.start();
    	}
    	
    	// 勝負の結果を表示した後に...
    	msg_lbl.setText(msg_lbl.getText() + " もう一回しますか？");

    	btn_high.setText("もう一回"); // ボタンの名前を変える
    	btn_low.setText("終了");

    	btn_high.setBackground(Color.LIGHT_GRAY);//ボタンの色を戻す
    	btn_low.setBackground(Color.LIGHT_GRAY);
    	btn_high.setOpaque(true);
    	btn_low.setOpaque(true);
    	
    	if (cmd.equals("もう一回")) //もう一回ボタンが押された時の処理
    	{
    		parent.Draw(new Deck()); // 親が新しいカードを引く
    		child.Draw(new Deck());  // 子が新しいカードを引く
    		
    		// 親カードを再表示
    		parent_no_lbl.setText(getNoStr(parent.GetNo()));
    		parent_suit_lbl.setIcon(getSuitIcon(parent.GetSuit()));

    		// 子カードは？に戻す
    		child_no_lbl.setText("?");
    		child_suit_lbl.setIcon(null);

    		msg_lbl.setText("HIGHかLOWか当ててください！"); //メッセージリセット

    		// ボタンを元の名前に戻す
    		btn_high.setText("HIGH");
    		btn_low.setText("LOW");
    		
    		return; //スコアリセットをここではしない
    	}
    	else if (cmd.equals("終了")) //終了ボタンが押された時の処理
    	{
    		SoundPlayer.playSound("end.wav"); // 終了音
    		System.exit(0); // ゲームを終了する
    	}

    	return;
    }
    
    //パネルのフォント設定を行うメソッド
    public static void setPanel(JPanel panel, Color color, BorderLayout layout, Dimension dimension)
    {
    	
    	panel.setBackground(color.darker());// 背景色を設定、色を暗くする
    	panel.setLayout(layout);            // レイアウトを設定
    	panel.setPreferredSize(dimension);  // 表示サイズを設定
    	
    	return;
    }
    
    //ラベルのフォント設定を行うメソッド
    public static void setLabelFont(JLabel label, Color clr, 
    		int x_pos, int y_pos,
    		int x_size, int y_size,
    		int strSize, boolean opq)
    {
    	label.setBackground(clr);        							 //背景色を設定
    	label.setLocation(x_pos, y_pos); 							 //表示位置を設定
    	label.setSize(x_size, y_size);   							 //表示サイズを設定
    	label.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, strSize)); //書式、文字サイズを設定
    	label.setHorizontalAlignment(JLabel.CENTER); 				 //水平方向中央揃え
    	label.setVerticalAlignment(JLabel.CENTER);   				 //垂直方向中央揃え
    	label.setOpaque(opq); 										 //ラベルの透明性を設定(true＝不透明、false＝透明)
    	
    	return;
    }
    
    //ボタンの設定を行うメソッド
    public static void setButton(JButton btn, ActionListener al, int x_size, int y_size, int strSize)
    {
    	btn.setPreferredSize(new Dimension(x_size, y_size));       //表示サイズを設定
    	btn.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, strSize)); //書式、文字サイズを設定
    	btn.addActionListener(al); 								   //ボタンが押された時のイベントを受け取れるように設定
    	
    	return;
    }
    
    //マークに応じたアイコンオブジェクトを取得するメソッド    
    public static ImageIcon getSuitIcon(int suit)
    {
        String path = null;
        switch(suit)
        {
            case 0: path = "/img/spade.png"; break;
            case 1: path = "/img/heart.png"; break;
            case 2: path = "/img/diamond.png"; break;
            case 3: path = "/img/clover.png"; break;
            default:
                System.out.println("⚠️ エラー: 無効なスーツ値 " + suit);
                return null;
        }

        // クラスパスから画像を取得する
        java.net.URL imgURL = Display.class.getResource(path);
        if (imgURL == null) {
            System.out.println("⚠️ 画像が見つかりません: " + path);
            return null;
        }
        System.out.println("✅ 画像が見つかりました: " + path);
        return new ImageIcon(imgURL);
    }
    
    //数字に応じた表示文字列を取得するメソッド
    public static String getNoStr(int no)
    {
    	switch(no)
    	{
    		case 1: //エース
    			return "A";
    		
    		case 11: //ジャック
    			return "J";
    		
    		case 12: //クイーン
    			return "Q";
    		
    		case 13: //キング
    			return "K";
    		
    		default: //上記以外は数字をそのまま文字列として出力する
    			return String.valueOf(no);
    	}
    }
    
    //スコアを更新するメソッド
    public void updateScore(boolean isWin) 
    {
        if (isWin) 
        {
            score++; // 勝ったらスコアを1増やす
        } 
        else if (!isWin && !btn_high.getText().equals("もう一回"))
        {
            score = 0; // 負けたらスコアを0にリセット
        }
        score_lbl.setText("スコア: " + score); //スコアを表示
    }
}
