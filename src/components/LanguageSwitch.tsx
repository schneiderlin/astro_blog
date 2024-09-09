import { useState } from "react"
import { Globe } from "lucide-react"
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select"
import { languages } from "@/i18n/ui"

interface LanguageSelectorProps {
  initialLang: string;
  customPaths: Record<string, string>;
}

export default function LanguageSelector({ initialLang = "en", customPaths }: LanguageSelectorProps) {
  const [selectedLang, setSelectedLang] = useState(initialLang)

  const handleLanguageChange = (langCode: string) => {
    setSelectedLang(langCode)
    // Redirect to the new language page using the custom path
    const newPath = customPaths[langCode] || `/${langCode}`
    window.location.href = newPath
  }

  return (
    <Select value={selectedLang} onValueChange={handleLanguageChange}>
      <SelectTrigger className="w-[200px]">
        <Globe className="mr-2 h-4 w-4 shrink-0" />
        <SelectValue placeholder="Select language" />
      </SelectTrigger>
      <SelectContent>
        {Object.entries(languages).map(([code, name]) => (
          <SelectItem key={code} value={code}>
            {name}
          </SelectItem>
        ))}
      </SelectContent>
    </Select>
  )
}