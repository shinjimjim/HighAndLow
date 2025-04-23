package main;

import javax.swing.ImageIcon;

//ゲームメインクラス
public class GameMain
{
    public static void main(String[] args)
    {
    	ImageIcon icon = Display.getSuitIcon(2); // ダイヤの画像を取得
        if (icon == null) {
            System.out.println("❌ 画像が取得できませんでした！");
        } else {
            System.out.println("✅ 画像が正常に取得できました！");
        }
    	
    	// BGMを再生
        SoundPlayer.playBGM("bgm.wav"); 
        
        //山札を生成
        Deck deck = new Deck();

        //親オブジェクトを生成し、親がカードを引く
        Player parent = new Player();
        parent.Draw(deck);

        //子オブジェクトを生成し、子がカードを引く
        Player child = new Player();
        child.Draw(deck);

        //ゲーム画面を生成
        new Display(parent, child);

        return;
    }
}
