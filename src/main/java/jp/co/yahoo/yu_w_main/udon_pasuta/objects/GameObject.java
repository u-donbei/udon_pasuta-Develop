/*
 * Copyright (c) 2023 ウードン (yu_w_main@yahoo.co.jp)
 */
package jp.co.yahoo.yu_w_main.udon_pasuta.objects;

import java.io.File;
import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import jp.co.yahoo.yu_w_main.udon_pasuta.constants.Facing;
import jp.co.yahoo.yu_w_main.udon_pasuta.constants.PathConsts;
import jp.co.yahoo.yu_w_main.udon_pasuta.panes.GameViewPane;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**物を表すクラス。
 * ゲーム内のものクラスは、必ずこのクラスを継承します。
 *
 *@author   渡邉雄宇
 *
 *@version 1.0
 *
 *
 */

public abstract class GameObject implements Cloneable, Serializable {
	@Serial
	private static final long serialVersionUID = 1L;
	/**x座標*/
	private double x;
	/**Y座標*/
	private double y;
	/**画像パス*/
	@JsonIgnore
	private File imagePath;

	public boolean isHited() {
		return isHited;
	}

	public void setHited(boolean hited) {
		isHited = hited;
	}

	/**当たり判定があるかどうか*/
	@JsonIgnore
	private boolean isHited;

	/**画像*/
	@JsonIgnore
	private final transient ImageView imageView;

	/**向き*/
	public Facing facing;
	/**名前*/
	private String name;

	/**画像を設定するコンストラクタ
	 * 
	 * @param image 画像パス*/
	public GameObject(File image) {
		imageView = new ImageView();
		setImage(image);
		setName(getClass().getName());
	}

	/**いろいろ設定するコンストラクタ
	 *
	 * @param image 画像パス
	 *
	 * @param name 名前*/
	public GameObject(File image, String name) {
		imageView = new ImageView();
		setImage(image);
		setName(name);
	}

	public void setName(String name) {
		this.name = name;
	}

	/**何も設定しないコンストラクタ。
	 *
	 * @deprecated 代わりにGameObject(File)を使用してください。*/
	@Deprecated
	public GameObject() {
		imageView = new ImageView();
	}

	/**画像・X座標・Y座標を設定するコンストラクタ
	 * 
	 * @param image 画像パス
	 * 
	 * @param x X座標
	 * 
	 * @param y Y座標
	 * 
	 * @deprecated 代わりにGameViewPane#addを使用してください
	 * 
	 * @see GameViewPane*/
	@Deprecated
	public GameObject(File image, double x, double y) {
		this(image);
		this.x = x;
		this.y = y;
	}

	/**X座標を設定する
	 * @param x X座標*/
	public final void setX(double x) {
		this.x = x;
	}

	/**X座標を取得する。
	 * 
	 * @return X座標*/
	public final double getX() {
		return x;
	}

	/**Y座標を設定する。
	 * 
	 * @param y Y座標*/
	public final void setY(double y) {
		this.y = y;
	}

	/**Y座標を取得する。
	 * 
	 * @return Y座標*/
	public final double getY() {
		return y;
	}

	/**画像を設定する。
	 * 
	 * @param image 画像パス*/
	public final void setImage(File image) {
		if(image.exists()){
			imagePath = image;
			imageView.setImage(new Image(imagePath.toURI().toString()));
		} else {
			imagePath = new File(PathConsts.TEXTURE_DIR.getPath() + "ntf.png");
			imageView.setImage(new Image(imagePath.toURI().toString()));
		}
	}

	/**画像パスを取得する。
	 * 
	 * @return 画像パス*/
	public final File getImage() {
		return imagePath;
	}
	
	/**画像ノードを取得する。
	 * 
	 * @return 画像ノード*/
	public final ImageView getImageView() {
		return imageView;
	}
	
	/**対象に当たっているかどうか
	 * 
	 * @param target 対象
	 * 
	 * @return 当たっているかどうか*/
	public final boolean isHit(GameObject target) {
		return isHited() && target.isHited() && (getImage() != null && target.getImage() != null)
				&& getImageView().getBoundsInParent().intersects(target.getImageView().getBoundsInParent());
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		super.clone();
		return SerializationUtils.clone(this);
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	@Override
	public boolean equals(Object obj){
		return EqualsBuilder.reflectionEquals(this, obj);
	}

	@Override
	public abstract String toString();

	/**各フレームに実行したいこと(<b>Templateパターン</b>を使う。*/
	public final void update(Runnable task){
		imageView.setTranslateY(y);
		imageView.setTranslateX(x);
		doUpdate();
		task.run();
	}

	public void moveY(double step) {
		setY(getY() + step);
	}

	public void moveX(double step) {
		setX(getX() + step);
	}

	protected void doUpdate() {

	}

	public static GameObject getEmptyInstance() {
		return new GameObject(new File(PathConsts.TEXTURE_DIR.getPath() + "air.png")) {
			@Override
			public String toString() {
				return "empty";
			}
		};
	}

	public String getName() {
		return name;
	}
}
