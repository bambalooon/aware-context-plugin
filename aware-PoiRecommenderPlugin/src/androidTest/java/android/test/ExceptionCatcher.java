package android.test;

/**
 * Name: ExceptionCatcher
 * Description: ExceptionCatcher allows to clean and simple get of thrown exception
 * Class created because it isn't possible to use fest-assert with Android,
 * because fest-assert has dependency on java's awt, which is unknown to Android
 * https://jira.codehaus.org/browse/FEST-485
 * Date: 2015-04-12
 * Created by BamBalooon
 */
public abstract class ExceptionCatcher {
    public Exception catchException() {
        try {
            invoke();
        } catch (Exception e) {
            return e;
        }
        return null;
    }

    protected abstract void invoke() throws Exception;
}
