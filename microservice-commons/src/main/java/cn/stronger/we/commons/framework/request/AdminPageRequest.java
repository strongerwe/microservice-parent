package cn.stronger.we.commons.framework.request;

import cn.stronger.we.commons.framework.ResultErrCodeI;
import cn.stronger.we.commons.validator.MatchCheck;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author qiang.w
 * @version 1.0.0
 * @class 分页Request
 * @department Platform Center
 * @date 2024-09-16 21:14
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class AdminPageRequest extends AbstractAdminRequest {
    private Integer pageNum = 1;
    private Integer pageSize = 10;
    private Boolean page = true;

    public void validatePageSize() {
        MatchCheck.isFalse((this.getPageSize() > ResultErrCodeI.MAX_PAGE_SIZE), ResultErrCodeI.PAGE_UP_MAX_SIZE_CODE, ResultErrCodeI.PAGE_UP_MAX_SIZE_MSG);
    }
}
