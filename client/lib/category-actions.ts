'use server';

import { revalidatePath } from "next/cache";
import { redirect } from "next/navigation";
import { z } from "zod";
import { CreateCategoryDto } from "@/lib/types";
import { categoryApiService } from "@/services/category-api-service";

const CreateCategoryFormSchema = z.object({
  name: z.string(),
  type: z.enum(['INCOME', 'EXPENSE']),
  icon: z.string()
});

export type State = {
  errors?: {
    name?: {
      errors?: string[] | null;
    },
    type?: {
      errors?: string[] | null;
    },
    icon?: {
      errors?: string[] | null;
    }
  };
  message?: string | null;
}

export async function createCategory(prevState: State, formData: FormData) {

  const validatedFields = CreateCategoryFormSchema.safeParse({
    name: formData.get('name'),
    type: formData.get('type'),
    icon: formData.get('icon')
  });

  if (!validatedFields.success) {
    const errorProperties = z.treeifyError(validatedFields.error).properties;

    return {
      errors: {
        name: errorProperties?.name,
        type: errorProperties?.type,
        icon: errorProperties?.icon
      },
      message: 'Атрибуты формы заполнены некорректно'
    } satisfies State;
  }

  const newCategory: CreateCategoryDto = validatedFields.data satisfies CreateCategoryDto;

  try {
    await categoryApiService.defineCategory(newCategory);
  } catch (error) {
    return {
      message: 'Произошла ошибка: Категория не была создана'
    } satisfies State;
  }

  revalidatePath("/categories");
  redirect("/categories");
}
