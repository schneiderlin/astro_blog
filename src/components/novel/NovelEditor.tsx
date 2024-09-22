import { EditorCommand, EditorCommandEmpty, EditorCommandItem, EditorCommandList, EditorContent, EditorRoot } from "novel";
import { useState, useEffect } from "react";
import defaultExtensions from "../novel/extensions";
import { slashCommand, suggestionItems } from "./slash";
import { handleCommandNavigation } from "novel/extensions";

const extensions = [...defaultExtensions, slashCommand];

interface TailwindEditorProps {
  initialContent?: any;
  onContentChange?: (content: any) => void;
}

const TailwindEditor: React.FC<TailwindEditorProps> = ({ initialContent, onContentChange }) => {
  const [content, setContent] = useState(initialContent);

  useEffect(() => {
    if (onContentChange) {
      onContentChange(content);
    }
  }, [content, onContentChange]);

  return (
    <EditorRoot>
      <EditorContent
        initialContent={content}
        extensions={extensions}
        onUpdate={({ editor }) => {
          const json = editor.getJSON();
          setContent(json);
        }}
        editorProps={{
          handleDOMEvents: {
            keydown: (_view, event) => handleCommandNavigation(event),
          },
          attributes: {
            class: `prose prose-lg dark:prose-invert prose-headings:font-title font-default focus:outline-none max-w-full border border-gray-300 dark:border-gray-700 rounded-md p-4`,
          }
        }}
      >
        <EditorCommand className='z-50 h-auto max-h-[330px]  w-72 overflow-y-auto rounded-md border border-muted bg-background px-1 py-2 shadow-md transition-all'>
          <EditorCommandEmpty className='px-2 text-muted-foreground'>No results</EditorCommandEmpty>
          <EditorCommandList>
            {suggestionItems.map((item) => (
              <EditorCommandItem
                value={item.title}
                onCommand={(val) => item.command(val)}
                className={`flex w-full items-center space-x-2 rounded-md px-2 py-1 text-left text-sm hover:bg-accent aria-selected:bg-accent `}
                key={item.title}>
                <div className='flex h-10 w-10 items-center justify-center rounded-md border border-muted bg-background'>
                  {item.icon}
                </div>
                <div>
                  <p className='font-medium'>{item.title}</p>
                  <p className='text-xs text-muted-foreground'>{item.description}</p>
                </div>
              </EditorCommandItem>
            ))}
          </EditorCommandList>
        </EditorCommand>
      </EditorContent>
    </EditorRoot>
  );
};

export default TailwindEditor;