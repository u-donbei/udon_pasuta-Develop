/*
 * Copyright (c) 2023 ウードン (yu_w_main@yahoo.co.jp)
 */
package jp.co.yahoo.yu_w_main.udon_pasuta.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**マネジャーであることを表す。
 * このアノテーションが付いたクラスなどは、○○Managerという名前になっている必要があります。
 * {@snippet id="usage" lang=java:
 * //使用例
 * @Manager
 * public class HogeManager {
 *     private HogeManager() { }
 *
 *     public static void hoge() {
 *          //処理
 *     }
 * }
 * //次の記述はエラーになります。
 * @Manager
 * public class Hoge {
 *     //...
 * }
 * }
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
public @interface Manager {
}
