import YooptaEditor, { createYooptaEditor } from '@yoopta/editor';
import Paragraph from '@yoopta/paragraph';
import { HeadingOne, HeadingThree, HeadingTwo } from '@yoopta/headings';
// import Blockquote from '@yoopta/blockquote';
import Code from '@yoopta/code';
import { Bold, Italic, Underline, Strike, CodeMark, Highlight } from '@yoopta/marks';
import ActionMenu, { DefaultActionMenuRender } from '@yoopta/action-menu-list';
import { NumberedList, BulletedList, TodoList } from '@yoopta/lists';
import Toolbar, { DefaultToolbarRender } from '@yoopta/toolbar';
import { useMemo } from 'react';

const plugins = [Paragraph, HeadingOne, HeadingTwo, HeadingThree, NumberedList, BulletedList, TodoList];

const marks = [Bold, Italic, Underline, Strike, CodeMark, Highlight];

const tools = {
    Toolbar: {
        tool: Toolbar,
        render: DefaultToolbarRender,
    },
    ActionMenu: {
        tool: ActionMenu,
        render: DefaultActionMenuRender,
    },
};

export default function Editor() {
    const editor = useMemo(() => createYooptaEditor(), []);

    return (
        <div>
            <YooptaEditor
                editor={editor}
                plugins={plugins}
                marks={marks}
                tools={tools}
            />
        </div>
    );
}