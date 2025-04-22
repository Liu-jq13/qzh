package com.kama.notes.model.vo.collection;

import lombok.Data;

@Data
public class CollectionVO {
    private Integer collectionId;
    private String name;
    private String description;
    /**
     * 查询收藏夹时，可能会携带的 noteId 参数，这个 noteStatus 可以用来判断该 note 是否被收藏
     */
    private NoteStatus noteStatus;

    @Data
    public static class NoteStatus {
        private Integer noteId;
        private Boolean isCollected;
    }
}
