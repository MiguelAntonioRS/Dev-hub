#!/usr/bin/env bash
set -euo pipefail

# Ensure we run from repository root
ROOT_DIR=$(git rev-parse --show-toplevel 2>/dev/null || true)
if [[ -z "${ROOT_DIR}" ]]; then
  ROOT_DIR="$(pwd)"
fi
cd "$ROOT_DIR"

# Get list of uncommitted changes (tracked and new)
CHANGES=$(git status --porcelain | awk '{print $2}')
if [[ -z "${CHANGES}" ]]; then
  echo "No changes to commit."
  exit 0
fi

count=0
for f in $CHANGES; do
  # skip empty lines
  [[ -z "$f" ]] && continue
  # Stage file
  git add -- "$f"
  # Build a descriptive message
  case "$f" in
    *.css) msg="style: per-file change $f" ;;
    *.html) msg="fix(auth): per-file readability for $f" ;;
    *.java) msg="refactor: per-file change $f" ;;
    *.xml|*.properties) msg="config: update $f" ;;
    *) msg="chore: update $f" ;;
  esac
  git commit -m "$msg"
  count=$((count+1))
done
echo "Committed ${count} file(s)."
