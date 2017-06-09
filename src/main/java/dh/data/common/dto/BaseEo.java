package dh.data.common.dto;


import dh.data.common.bean.Beans;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseEo<T> {
    private Logger log = LoggerFactory.getLogger(BaseEo.class);

    protected abstract void processBean(T paramT);

    protected void fromModel(T from) {
        this.log.debug("From Model [{}] content [{}]", from.getClass().getName(), from.toString());
        try {
            Beans.copy(from, this);
            processBean(from);
        } catch (Exception ex) {
            this.log.error("Model reflection copy (all) error [{}] ! ", ExceptionUtils.getStackTrace(ex));
        }
        this.log.debug("To Model [{}] after process content [{}]", getClass().getName(), toString());
    }
}
