-- Add missing updated_at column to user_key table
ALTER TABLE "user_key"
ADD COLUMN "updated_at" timestamp DEFAULT (now());
