import {
  Dialog,
  DialogPanel,
  Combobox,
  ComboboxInput,
  ComboboxOptions,
  ComboboxOption,
  Transition,
  TransitionChild,
  DialogBackdrop,
} from "@headlessui/react";
import { Fragment, useEffect, useState } from "react";
import { SearchIcon } from "@heroicons/react/outline";

export default function CommandPalette() {
  const [isOpen, setIsOpen] = useState(false);

  useEffect(() => {
    function onKeydown(event: any) {
      if (event.key === "q" && (event.metaKey || event.ctrlKey)) {
        setIsOpen(!isOpen);
      }
    }

    window.addEventListener("keydown", onKeydown);
    return () => {
      window.removeEventListener("keydown", onKeydown);
    };
  }, [isOpen]);

  return (
    <Transition show={isOpen} as={Fragment}>
      <Dialog
        onClose={() => {
          setIsOpen(false);
        }}
        className="fixed inset-0 p-4 pt-[25vh] overflow-y-auto"
      >
        <DialogBackdrop className="fixed inset-0 bg-gray-500/75"></DialogBackdrop>
        <TransitionChild
          enter="duration-300 ease-out"
          enterFrom="opacity-0"
          enterTo="opacity-100"
          leave="duration-200 ease-in"
          leaveFrom="opacity-100"
          leaveTo="opacity-0"
        >
          <DialogPanel>
            <Combobox
              as="div"
              onChange={(value) => {
                setIsOpen(false);
              }}
              className="relative overflow-hidden bg-slate-200 max-w-xl mx-auto rounded-xl shadow-2xl ring-1 ring-black/5 divide-y divide-gray-100"
            >
              {({ activeOption }) => {
                return (
                  <>
                    <div className="flex items-center px-4">
                      <SearchIcon className="h-6 w-6 text-gray-500"></SearchIcon>
                      <ComboboxInput
                        autoFocus
                        className="px-2 h-12 w-full border-none outline-none ring-lime-100 bg-transparent focus:ring-0 text-sm text-gray-800 placeholder-gray-400"
                        placeholder="Search..."
                      ></ComboboxInput>
                    </div>
                    <ComboboxOptions
                      static
                      className="py-4 text-sm max-h-96 overflow-y-auto"
                    >
                      <ComboboxOption key="option1" value={"option1"}>
                        <div
                          className={`px-4 py-2 space-x-1 ${
                            activeOption === "option1"
                              ? "bg-indigo-600"
                              : "bg-white"
                          }`}
                        >
                          <span
                            className={`font-medium ${
                              activeOption === "option1"
                                ? "text-white"
                                : "text-gray-900"
                            }`}
                          >
                            title{" "}
                          </span>
                        </div>
                      </ComboboxOption>
                    </ComboboxOptions>
                  </>
                );
              }}
            </Combobox>
          </DialogPanel>
        </TransitionChild>
      </Dialog>
    </Transition>
  );
}
