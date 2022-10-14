package hello.blog.repository.paging;

import lombok.Data;

import java.util.List;

@Data
public class Page<T> {
    private int dataNumPerPage;
    private int curPageNum;
    private int totalPageNum;
    private List<T> pageData;

    public Page(int dataNumPerPage, int page, int totalDataSize) {
        this.dataNumPerPage = dataNumPerPage;
        totalPageNum = totalDataSize / dataNumPerPage;
        if (totalDataSize % dataNumPerPage > 0) {
            totalPageNum++;
        }
        if (page <= 1) {
            curPageNum = 1;
        } else if (page > totalPageNum) {
            curPageNum = totalPageNum;
        } else {
            curPageNum = page;
        }
    }

    public int getOffset() {
        return (curPageNum - 1) * dataNumPerPage;
    }

    public boolean hasPrev() {
        if (curPageNum > 1) {
            return true;
        }
        return false;
    }

    public boolean hasNext() {
        if (curPageNum < totalPageNum) {
            return true;
        }
        return false;
    }

    public int getPrevPageNum() {
        if (hasPrev()) {
            return curPageNum - 1;
        }
        return curPageNum;
    }

    public int getNextPageNum() {
        if (hasNext()) {
            return curPageNum + 1;
        }
        return curPageNum;
    }
}
