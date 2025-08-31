import { iconMap, IconName } from '@/lib/icon-map';
import { AlertCircle } from 'lucide-react';

interface CategoryIconProps {
  name: IconName;
  size?: number;
}

export function CategoryIcon({ name, size = 20 }: CategoryIconProps) {
  const IconComponent = iconMap[name];

  if (!IconComponent) {
    return <AlertCircle size={size} />;
  }

  return <IconComponent size={size} />;
}