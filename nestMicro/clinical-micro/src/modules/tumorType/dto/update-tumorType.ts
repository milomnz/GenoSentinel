import { PartialType } from "@nestjs/swagger";
import { CreateTumorTypeDto } from "./create-tumorType";

export class UpdateTumorTypeDto extends PartialType(CreateTumorTypeDto) {}