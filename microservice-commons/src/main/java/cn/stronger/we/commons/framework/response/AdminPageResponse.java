package cn.stronger.we.commons.framework.response;

import com.github.pagehelper.PageInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author qiang.w
 * @version 1.0.0
 * @class 分页List
 * @department Platform Center
 * @date 2024-09-16 21:06
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class AdminPageResponse<R> implements SuperResponse {
    /**
     * 当前页
     */
    private int pageNum;

    /**
     * 每页的数量
     */
    private int pageSize;

    /**
     * 当前页的数量
     */
    private int size;

    /**
     * 总页数
     */
    private int pages;

    /**
     * 总数
     */
    private long total;

    /**
     * 结果集
     */
    private List<R> results;

    public AdminPageResponse(PageInfo<?> page, List<R> list) {
        this.size = page.getSize();
        this.pageSize = page.getPageSize();
        this.pageNum = page.getPageNum();
        this.pages = page.getPages();
        this.total = page.getTotal();
        this.results = list;
    }
}
